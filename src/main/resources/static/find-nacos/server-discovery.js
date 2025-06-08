// server-discovery.js
class NacosDiscoveryClient {
    constructor(config) {
        this.serverAddr = config.serverAddr;
        this.namespace = config.namespace || 'public';
        this.serviceInstances = new Map(); // 存储服务实例（服务名 -> 实例列表）
        this.initialized = false; // 初始化完成标志
    }

    async init() {
        // 定义订阅的服务列表（确保与后端注册的服务名一致）
        this.subscribedServices = ['product-service', 'order-service', 'user-service'];
        await this.updateSubscribedServices(); // 首次同步加载实例
        this.initialized = true; // 标记初始化完成
        console.log('Nacos 服务发现初始化完成');

        // 开启定时更新（30秒）
        setInterval(() => this.updateSubscribedServices(), 30000);
    }

    // 更新所有订阅的服务实例（改为并行请求，提升效率）
    async updateSubscribedServices() {
        // 使用 Promise.all 并行获取多个服务实例
        await Promise.all(
            this.subscribedServices.map(serviceName => this.updateServiceInstances(serviceName))
        );
    }

    // 更新单个服务的实例（增加超时处理）
    async updateServiceInstances(serviceName) {
        try {
            const url = `${this.serverAddr}/nacos/v1/ns/instance/list?serviceName=${serviceName}&namespaceId=${this.namespace}`;
            const response = await fetch(url, { timeout: 5000 }); // 添加超时（5秒）
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

            const data = await response.json();
            const instances = data.hosts
                .filter(host => host.healthy) // 过滤健康实例
                .map(host => ({
                    ip: host.ip,
                    port: host.port,
                    metadata: host.metadata // 包含负载等元数据
                }));

            this.serviceInstances.set(serviceName, instances); // 存储实例列表
            console.log(`Updated instances for ${serviceName}:`, instances);
        } catch (error) {
            console.error(`Failed to update ${serviceName}:`, error);
        }
    }

    // 获取服务实例（增加空值校验）
    getInstances(serviceName) {
        const instances = this.serviceInstances.get(serviceName) || [];
        if (instances.length === 0 && this.initialized) {
            console.warn(`警告：服务 ${serviceName} 无可用实例`);
        }
        return instances;
    }
}

// 初始化客户端（传入 Nacos 地址）
export const nacosClient = new NacosDiscoveryClient({
    serverAddr: 'http://192.168.172.1:8848',
    namespace: 'public'
});

// 导出初始化 Promise（关键修改：等待 init() 完成后再暴露客户端）
export const nacosReady = nacosClient.init().catch(error => {
    console.error('Nacos 初始化失败:', error);
    throw error; // 抛出错误，阻止后续操作
});