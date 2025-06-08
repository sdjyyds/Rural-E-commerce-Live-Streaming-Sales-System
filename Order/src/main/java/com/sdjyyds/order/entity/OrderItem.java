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
// OrderItem实体类
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal actualPrice;
    private String refundStatus;
    private Date createdAt;
    private Date updatedAt;
}
