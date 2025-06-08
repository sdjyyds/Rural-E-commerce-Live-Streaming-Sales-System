package com.sdjyyds.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class Report {
    private Long id;
    private Long reporterId;
    private String targetType;
    private Long targetId;
    private String reason;
    private String description;
    private String evidenceUrls;
    private String status;
    private Long processedBy;
    private Date processedAt;
    private String resultRemark;
    private Date createdAt;
    private Date updatedAt;
}

