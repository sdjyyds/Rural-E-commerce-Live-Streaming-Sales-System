package com.sdjyyds.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.sdjyyds.admin.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}