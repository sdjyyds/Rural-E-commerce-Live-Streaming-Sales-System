package com.sdjyyds.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class OperationLog {
    private Long id;
    private Long operatorId;
    private String operatorType;
    private String operationType;
    private String targetType;
    private Long targetId;
    private String operationDetail;
    private String ipAddress;
    private String userAgent;
    private Date createdAt;
}

