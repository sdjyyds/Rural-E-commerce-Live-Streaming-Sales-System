package com.sdjyyds.admin.mapper;

import com.sdjyyds.admin.entity.AuditLog;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface AuditLogMapper {
    void insert(AuditLog auditLog);
    List<AuditLog> selectAll();
}