package com.sdjyyds.user.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * JwtUserDetails 类实现 Spring Security 的 UserDetails 接口，用于封装用户认证信息。
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class JwtUserDetails implements UserDetails {

    private final Long userId; // 用户唯一标识
    private final String username; // 用户名
    private final String role; // 用户角色
    private final Collection<? extends GrantedAuthority> authorities; // 用户权限集合

    /**
     * 获取用户密码。由于使用 JWT 认证，不在此处提供密码。
     *
     * @return null（JWT 模式下无需密码）
     */
    @Override
    public String getPassword() {
        return null;
    }

    /**
     * 判断账户是否未过期。
     *
     * @return true（表示账户有效）
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断账户是否未被锁定。
     *
     * @return true（表示账户未锁定）
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 判断凭证是否未过期。
     *
     * @return true（表示凭证有效）
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断用户是否启用。
     *
     * @return true（表示用户启用状态）
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
