package com.sdjyyds.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO类，用于封装用户修改密码时的请求数据
 */
@Data
public class PasswordChangeDTO {
    /**
     * 旧密码，不能为空
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码，不能为空且长度需在6到20位之间
     */
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String newPassword;
}

