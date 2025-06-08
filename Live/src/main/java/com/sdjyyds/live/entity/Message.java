package com.sdjyyds.live.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class Message {
    private Long id;
    private Long streamId;
    private Long userId;
    private String content;
    private Date sentTime;
}

