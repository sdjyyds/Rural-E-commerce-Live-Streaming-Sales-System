package com.sdjyyds.admin.service;

import com.sdjyyds.admin.entity.AuditLog;
import com.sdjyyds.admin.mapper.AuditLogMapper;
import com.sdjyyds.admin.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {
    @Autowired
    private AuditLogMapper auditLogMapper;
    @Autowired
    private SnowflakeIdGenerator idGenerator;
    @Override
    public void recordAudit(AuditLog auditLog) {
        auditLog.setId(idGenerator.nextId());
        auditLog.setReviewedAt(new Date());
        auditLogMapper.insert(auditLog);
    }

    @Override
    public List<AuditLog> listAll() {
        return auditLogMapper.selectAll();
    }
}
