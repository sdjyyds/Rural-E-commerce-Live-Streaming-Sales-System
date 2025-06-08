package com.sdjyyds.user.dto;

import lombok.Data;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class AddressVO {
    // 地址唯一标识
    private String id;
    // 收件人姓名
    private String recipientName;
    // 联系电话
    private String phone;
    // 省份
    private String province;
    // 城市
    private String city;
    // 区县
    private String district;
    // 详细地址
    private String detailAddress;
    // 是否为默认地址
    private Boolean isDefault;
}
