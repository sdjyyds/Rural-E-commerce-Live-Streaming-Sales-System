package com.sdjyyds.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class UserDetailVO {
    private String username;
    private String realName;
    private String phone;
    private String email;
    private String avatarUrl;
    private Boolean verified;
    private Date createdAt;
    private Date updatedAt;
}
