package com.sdjyyds.user.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class UserLoginDTO {

    // 用户名和手机号至少有一个不为空
    @Size(min = 5, max = 20, message = "用户名长度5-20位")
    private String username;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    @NotBlank(message = "用户类型不能为空")
    private String type;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String password;
    // 自定义验证逻辑：确保用户名或手机号至少有一个不为空
    public boolean isValidLogin() {
        return (username != null && !username.isEmpty()) || (phone != null && !phone.isEmpty());
    }
}
