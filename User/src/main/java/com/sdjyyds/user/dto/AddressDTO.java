package com.sdjyyds.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * AddressDTO 用于封装地址信息的数据传输对象。
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class AddressDTO {
    /**
     * 收件人姓名，不能为空，最大长度为50。
     */
    @NotBlank(message = "收件人不能为空")
    @Size(max = 50, message = "收件人姓名不超过50字")
    private String recipientName;

    /**
     * 联系电话，必须符合手机号格式（以13-19开头的11位数字）。
     */
    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 省份，不能为空。
     */
    @NotBlank(message = "省份不能为空")
    private String province;

    /**
     * 城市，不能为空。
     */
    @NotBlank(message = "城市不能为空")
    private String city;

    /**
     * 区域，可为空。
     */
    private String district;

    /**
     * 详细地址，不能为空。
     */
    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    /**
     * 是否为默认地址，可为空。
     */
    private Boolean isDefault;
}

