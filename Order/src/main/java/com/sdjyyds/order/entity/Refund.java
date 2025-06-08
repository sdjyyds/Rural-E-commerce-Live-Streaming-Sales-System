package com.sdjyyds.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

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
// Refund实体类
public class Refund {
    private Long id;
    private Long orderId;
    private Long userId;
    private String refundNo;
    private String reason;
    private BigDecimal amount;
    private String status;
    private Date appliedAt;
    private Date processedAt;
    private Long processedBy;
    private String rejectReason;
    private Date createdAt;
    private Date updatedAt;
}
