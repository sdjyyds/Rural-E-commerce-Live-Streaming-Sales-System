package com.sdjyyds.user.jwt;

import com.sdjyyds.user.util.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import com.alibaba.fastjson.JSON;

/**
 * 自定义JWT认证失败处理类，用于返回未授权的响应
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当用户未认证或认证已过期时，返回401未授权错误信息
     *
     * @param request       HTTP请求对象
     * @param response      HTTP响应对象
     * @param authException 认证异常信息
     * @throws IOException 如果响应写入过程中发生I/O错误
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 设置响应内容类型为JSON，并指定字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        // 设置HTTP状态码为401（未授权）
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 将错误信息以JSON格式写入响应体中
        response.getWriter().write(JSON.toJSONString(
                ResponseResult.error(401, "未认证或认证已过期")));
    }
}
