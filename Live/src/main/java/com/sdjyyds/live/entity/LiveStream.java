package com.sdjyyds.live.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class LiveStream {
    private Long id;
    private Long merchantId;
    private String title;
    private String coverUrl;
    private String streamUrl;
    private Date startTime;
    private Date endTime;
    private String status;
    private Integer viewerCount;
    private Integer likeCount;
    private Date createdAt;
    private Date updatedAt;
}

