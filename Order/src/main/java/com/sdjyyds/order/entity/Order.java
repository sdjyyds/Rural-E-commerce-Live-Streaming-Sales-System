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
// Order实体类
public class Order {
    private Long id;
    private String orderNo;
    private Long userId;
    private Date orderTime;
    private String status;
    private BigDecimal totalPrice;
    private BigDecimal paymentAmount;
    private String addressJson;
    private String remark;
    private Date createdAt;
    private Date updatedAt;
}
