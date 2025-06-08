package com.sdjyyds.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class OrderPaymentDetailDTO {

    private Long orderId;              // 订单ID
    private Long userId;               // 用户ID
    private BigDecimal totalPrice;     // 订单总金额
    private BigDecimal paymentAmount;  // 实付金额
    private String method;             // 支付方式（alipay、wechat、bank、balance）
    private String payStatus;          // 支付状态（pending、success、failed、closed）
    private String address;            // 收货地址快照（JSON格式字符串）

    private Integer quantity;          // 商品购买数量
    private BigDecimal unitPrice;      // 下单时单价
    private BigDecimal actualPrice;    // 实际支付单价
    private String productName;        // 商品名称
    private Long productId;            // 商品ID
    private String productImage;       // 商品图片快照
    private Integer stock;
    private Integer sales;
}

