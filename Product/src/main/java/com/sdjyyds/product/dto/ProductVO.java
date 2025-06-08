package com.sdjyyds.product.dto;

import com.sdjyyds.product.entity.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class ProductVO {
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
    private Boolean befavorited;

    public static ProductVO tranlate(Product product)
    {
        ProductVO productVO = new ProductVO();
        productVO.setId(product.getId());
        productVO.setMerchantId(product.getMerchantId());
        productVO.setName(product.getName());
        productVO.setCategory(product.getCategory());
        productVO.setDescription(product.getDescription());
        productVO.setPrice(product.getPrice());
        productVO.setOriginalPrice(product.getOriginalPrice());
        productVO.setStock(product.getStock());
        productVO.setSales(product.getSales());
        productVO.setMainImageUrl(product.getMainImageUrl());
        productVO.setImageUrls(product.getImageUrls());
        productVO.setStatus(product.getStatus());
        productVO.setCreatedAt(product.getCreatedAt());
        productVO.setUpdatedAt(product.getUpdatedAt());
        return productVO;
    }
}
