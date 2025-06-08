package com.sdjyyds.product.entity;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Data
public class Merchant{
    private Long id;
    private Long userId;
    private String businessName;
    private String businessLicense;
    private String businessLicenseImage;
    private String storeImage;
    private String contactPerson;
    private String contactPhone;
    private String email;
    private String address;
    private String status;
    private Date reviewedAt;
    private Long reviewedBy;
    private Date createdAt;
    private Date updatedAt;
}