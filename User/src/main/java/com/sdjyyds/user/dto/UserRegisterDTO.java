package com.sdjyyds.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class UserRegisterDTO {
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String password;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[0-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    @NotBlank(message = "用户名不能为空")
    @Size(min = 5, max = 20, message = "用户名长度2-10位")
    private String username;
}
