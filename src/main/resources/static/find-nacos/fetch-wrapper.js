// fetch-wrapper.js
import { nacosClient, nacosReady } from './server-discovery.js';

class ServiceDiscoveryFetch {
    constructor() {
        this.nacosClient = nacosClient;
    }

    // 解析服务名（从 URL 中提取，如 http://product-service/api）
    _parseServiceName(url) {
        return url.match(/^http:\/\/([^/]+)/)?.[1];
    }

    // 选择实例（增加初始化等待）
    async _selectInstance(serviceName) {
        await nacosReady; // 关键：确保初始化完成后再获取实例
        const instances = this.nacosClient.getInstances(serviceName);
        if (instances.length === 0) {
            throw new Error(`No instances available for service: ${serviceName}`);
        }
        // 简单随机算法（可替换为轮询、权重等策略）
        return instances[Math.floor(Math.random() * instances.length)];
    }

    async fetch(url, options = {}) {
        const serviceName = this._parseServiceName(url);
        if (!serviceName) return fetch(url, options); // 非服务 URL 直接请求

        try {
            await nacosReady; // 确保Nacos初始化完成
            const instance = await this._selectInstance(serviceName);
            const resolvedUrl = url.replace(
                `http://${serviceName}`,
                `http://${instance.ip}:${instance.port}`
            );
            console.log(`Resolved URL: ${url} -> ${resolvedUrl}`);
            return fetch(resolvedUrl, options);
        } catch (error) {
            console.error(`服务发现失败: ${error.message}`);
            throw new Error('服务不可用，请稍后重试'); // 不再回退到原始URL
        }
    }
}
const fetchWrapper = new ServiceDiscoveryFetch(nacosClient);
export default fetchWrapper;