package com.sdjyyds.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class UserUpdateDTO {
    @Size(max = 50, message = "真实姓名长度不超过50")
    private String realName;
    @Size(max = 20, message = "手机号长度不超过20")
    private String phone;
    @Size(max = 50, message = "邮箱长度不超过50")
    private String email;
}
