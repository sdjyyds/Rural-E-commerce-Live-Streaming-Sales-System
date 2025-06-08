package com.sdjyyds.user.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtTokenProvider 类用于生成、解析和验证 JWT（JSON Web Token）。
 * 使用 io.jsonwebtoken 库实现 JWT 的相关操作。
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    /**
     * 从配置文件中注入密钥，用于签名 JWT。
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 从配置文件中注入 token 过期时间（单位：毫秒）。
     */
    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * 生成 JWT token
     *
     * @param userId   用户 ID
     * @param username 用户名
     * @param role     用户角色
     * @return 返回生成的 token 字符串
     */
    public String generateToken(Long userId, String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        log.info("Generating token for userId: {}, username: {}, role: {}", userId, username, role);
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从 token 中提取用户 ID。
     *
     * @param token JWT token
     * @return 返回用户 ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        log.info("Getting userId from token: {}", claims.get("userId"));
        return claims.get("userId", Long.class);
    }

    /**
     * 从 token 中提取用户名。
     *
     * @param token JWT token
     * @return 返回用户名
     */
    public String getUsernameFromToken(String token) {
        log.info("Getting username from token: {}", Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 验证 token 是否有效。
     *
     * @param token JWT token
     * @return 如果 token 有效返回 true，否则返回 false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            log.info("Token is valid.");
            return true;
        } catch (Exception ex) {
            log.error("Invalid token: {}", ex.getMessage());
            return false;
        }
    }

    /**
     * 获取当前使用的密钥。
     *
     * @return 返回密钥字符串
     */
    public String getSecret() {
        return this.secret;
    }
}
