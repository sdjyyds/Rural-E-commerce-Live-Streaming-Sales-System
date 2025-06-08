package com.sdjyyds.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @MapperScan 注解用于指示 MyBatis 框架在指定包路径下扫描 Mapper 接口。
 * 这些接口会被自动注册为 Spring 容器中的 Bean，便于注入和使用。
 * 扫描的包路径为 "com.sdjyyds.product.dao"。
 */
@MapperScan("com.sdjyyds.product.dao")

/**
 * @SpringBootApplication 是 Spring Boot 的核心注解，标记该类为 Spring Boot 应用的主配置类。
 * 它是以下三个注解的组合：
 * - @Configuration：表示该类是一个配置类。
 * - @EnableAutoConfiguration：启用 Spring Boot 的自动配置机制。
 * - @ComponentScan：启用组件扫描，扫描当前包及其子包中的组件。
 */
@SpringBootApplication

/**
 * @EnableDiscoveryClient 用于启用服务发现功能。
 * 当应用启动时，它会自动向服务注册中心（如 Nacos、Eureka）注册自身信息，
 * 并能够从注册中心获取其他微服务的信息，实现服务间的通信与协作。
 */
@EnableDiscoveryClient
public class ProductApplication {

    /**
     * main 方法是程序的入口点。
     * 调用 SpringApplication.run() 启动 Spring Boot 应用。
     *
     * @param args 命令行参数，用于传递启动时的可选参数。
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
