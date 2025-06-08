package com.sdjyyds.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
// Payment实体类
public class Payment {
    private Long id;
    private Long orderId;
    private Long userId;
    private String paymentNo;
    private Date paymentTime;
    private BigDecimal amount;
    private String method;
    private String status;
    private Date callbackTime;
    private String callbackContent;
    private Date createdAt;
    private Date updatedAt;
}
