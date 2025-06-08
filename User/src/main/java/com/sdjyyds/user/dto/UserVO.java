package com.sdjyyds.user.dto;

import lombok.Data;

/**
 * 用户信息展示对象（View Object），用于向前端传递用户相关数据
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class UserVO {
    /**
     * 用户登录名
     */
    private String username;

    /**
     * 用户角色权限标识
     */
    private String role;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户手机号码
     */
    private String phone;

    /**
     * 用户头像图片URL地址
     */
    private String avatarUrl;

    /**
     * 是否已完成实名认证
     */
    private Boolean verified;
}
