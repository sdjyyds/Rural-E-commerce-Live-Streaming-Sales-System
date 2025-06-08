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
// Product实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private Long merchantId;
    private String name;
    private String category;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer stock;
    private Integer sales;
    private String mainImageUrl;
    private String imageUrls; // JSON格式字符串
    private String status;
    private Date createdAt;
    private Date updatedAt;
}

