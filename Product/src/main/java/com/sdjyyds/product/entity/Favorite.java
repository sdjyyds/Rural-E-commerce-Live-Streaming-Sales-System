package com.sdjyyds.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
// Favorite实体类
public class Favorite {
    private Long id;
    private Long userId;
    private Long productId;
    private Date createdAt;
}
