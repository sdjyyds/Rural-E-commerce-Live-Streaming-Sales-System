package com.sdjyyds.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class AuditLog {
    private Long id;
    private Long adminId;
    private String targetType;
    private Long targetId;
    private String action;
    private String comments;
    private Date reviewedAt;
}

