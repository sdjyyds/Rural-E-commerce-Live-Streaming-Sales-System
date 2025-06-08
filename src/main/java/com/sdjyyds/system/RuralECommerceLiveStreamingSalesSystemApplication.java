package com.sdjyyds.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RuralECommerceLiveStreamingSalesSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuralECommerceLiveStreamingSalesSystemApplication.class, args);
    }

}
