package com.sdjyyds.admin.controller;

import com.sdjyyds.admin.entity.AuditLog;
import com.sdjyyds.admin.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/admin/audits")
public class AuditLogController {
    @Autowired
    private AuditLogService auditLogService;

    @PostMapping("/record")
    public ResponseEntity<?> recordAudit(@RequestBody AuditLog auditLog) {
        auditLogService.recordAudit(auditLog);
        return ResponseEntity.ok("Audit recorded.");
    }

    @GetMapping
    public List<AuditLog> listAudits() {
        return auditLogService.listAll();
    }
}
