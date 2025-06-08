package com.sdjyyds.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 地址实体类，用于表示用户的收货地址信息
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    /**
     * 地址的唯一标识ID
     */
    private Long id;

    /**
     * 关联的用户ID
     */
    private Long userId;

    /**
     * 收件人姓名
     */
    private String recipientName;

    /**
     * 收件人联系电话
     */
    private String phone;

    /**
     * 省份信息
     */
    private String province;

    /**
     * 城市信息
     */
    private String city;

    /**
     * 区县信息
     */
    private String district;

    /**
     * 详细地址描述
     */
    private String detailAddress;

    /**
     * 是否为默认地址
     */
    private Boolean isDefault;

    /**
     * 地址创建时间
     */
    private Date createdAt;

    /**
     * 地址最后更新时间
     */
    private Date updatedAt;
}
