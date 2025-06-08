package com.sdjyyds.user.util;

import com.sdjyyds.user.exception.BusinessException;
import com.sdjyyds.user.jwt.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public class SecurityUtils {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((JwtUserDetails) authentication.getPrincipal()).getUserId();
        }
        throw new BusinessException("未认证用户");
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((JwtUserDetails) authentication.getPrincipal()).getUsername();
        }
        throw new BusinessException("未认证用户");
    }
}