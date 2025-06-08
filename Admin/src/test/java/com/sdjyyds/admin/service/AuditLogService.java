package com.sdjyyds.admin.service;

import com.sdjyyds.admin.entity.AuditLog;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface AuditLogService {
    void recordAudit(AuditLog auditLog);
    List<AuditLog> listAll();
}
