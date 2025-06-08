package com.sdjyyds.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class User {
    private Long id;
    private String username;
    private String passwordHash;
    private String salt;
    private String role;
    private String realName;
    private String phone;
    private String email;
    private String avatarUrl;
    private Boolean isVerified;
    private Date createdAt;
    private Date updatedAt;
}
