package com.sdjyyds.admin.controller;

import com.sdjyyds.admin.entity.OperationLog;
import com.sdjyyds.admin.service.OperationLogService;
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
@RequestMapping("/admin/operations")
public class OperationLogController {
    @Autowired
    private OperationLogService operationLogService;

    @PostMapping("/log")
    public ResponseEntity<?> logOperation(@RequestBody OperationLog log) {
        operationLogService.logOperation(log);
        return ResponseEntity.ok("Operation logged.");
    }
    @GetMapping
    public List<OperationLog> getLogs() {
        return operationLogService.getAllLogs();
    }
}

