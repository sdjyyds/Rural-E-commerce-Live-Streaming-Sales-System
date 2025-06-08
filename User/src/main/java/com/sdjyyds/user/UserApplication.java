package com.sdjyyds.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务应用程序类
 * 该类是Spring Boot的入口类，通过Spring Application.run方法启动应用
 * 使用@SpringBootApplication注解启用Spring Boot的自动配置
 * 使用@MapperScan注解指定MyBatis Plus的Mapper接口所在包
 * 使用@EnableDiscoveryClient注解将该应用注册为Eureka客户端
 */
@MapperScan("com.sdjyyds.user.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication {

    /**
     * 主函数入口
     * 该函数通过SpringApplication.run方法启动Spring Boot应用
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}

