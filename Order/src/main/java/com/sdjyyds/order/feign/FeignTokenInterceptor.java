package com.sdjyyds.order.feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component
public class FeignTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 从当前请求中获取 HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 从请求头中获取 JWT Token（与 user-service 期望的方式一致）
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null) {
                // 将 Token 添加到 Feign 的请求头中
                requestTemplate.header(HttpHeaders.AUTHORIZATION, token);
            }
        }
    }
}