package com.sdjyyds.user.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 允许特定来源（前端域名）
        config.addAllowedOriginPattern("http://localhost:*"); // 允许所有 localhost 端口

        // 允许的请求头
        config.addAllowedHeader("*");

        // 允许的请求方法
        config.addAllowedMethod("*");

        // 允许携带凭证（如 Cookie、Token）
        config.setAllowCredentials(true);

        // 暴露的响应头
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Set-Cookie");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 对所有接口都有效

        return new CorsFilter(source);
    }
}