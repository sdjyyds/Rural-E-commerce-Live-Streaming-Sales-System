package com.sdjyyds.user.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.alibaba.cloud.commons.lang.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * JwtAuthenticationFilter 类用于处理 JWT 认证逻辑，继承 OncePerRequestFilter 确保每次请求只过滤一次。
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    /**
     * 构造函数注入 JwtTokenProvider 实例，用于解析和验证 Token。
     */
    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * 核心过滤方法，在每次请求时执行。
     * 从请求头中提取 Token，若存在且有效，则解析用户信息并设置到安全上下文中。
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request); // 提取 Token
            if (StringUtils.isNotBlank(token) && tokenProvider.validateToken(token)) { // 验证 Token 是否有效
                String username = tokenProvider.getUsernameFromToken(token); // 获取用户名
                Long userId = tokenProvider.getUserIdFromToken(token); // 获取用户 ID
                String role = Jwts.parser()
                        .setSigningKey(tokenProvider.getSecret()) // 使用签名密钥解析 Token
                        .parseClaimsJws(token)
                        .getBody()
                        .get("role", String.class); // 获取角色信息

                // 创建授权列表，添加前缀 ROLE_
                List<GrantedAuthority> authorities = Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + role));

                // 创建 UserDetails 对象，用于封装用户信息
                UserDetails userDetails = new JwtUserDetails(userId, username, role, authorities);

                // 创建 Authentication 对象并设置到安全上下文
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("用户 {} 认证通过", username);
            }
        } catch (Exception ex) {
            log.error("认证失败：{}", ex.getMessage());
            // 认证失败由入口点处理
        }

        filterChain.doFilter(request, response); // 继续后续过滤器链
    }

    /**
     * 从请求头中获取 Bearer Token，并去除前缀 "Bearer "。
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
