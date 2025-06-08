package com.sdjyyds.live.entity;
import lombok.Data;

import java.util.Date;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class Streamer  {
    private Long id;
    private Long userId;
    private String nickname;
    private String realName;
    private String idCardNumber;
    private String idCardFrontUrl;
    private String idCardBackUrl;
    private String avatarUrl;
    private String coverUrl;
    private String shortIntro;
    private String detailedIntro;
    private String contactPhone;
    private String email;
    private String bankAccount;
    private String bankName;
    private String accountHolder;
    private String socialMedia;
    private Integer followerCount;
    private Integer totalLikes;
    private String status;
    private Integer isCertified;
    private Date certifiedAt;
    private Date reviewedAt;
    private Long reviewedBy;
    private Date createdAt;
    private Date updatedAt;
}
