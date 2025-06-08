package com.sdjyyds.live.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class LiveStreamProduct {
    private Long id;
    private Long streamId;
    private Long productId;
    private Integer sortOrder;
    private BigDecimal discountPrice;
    private Date createdAt;
}

