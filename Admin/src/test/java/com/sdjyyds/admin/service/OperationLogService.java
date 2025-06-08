package com.sdjyyds.admin.service;

import com.sdjyyds.admin.entity.OperationLog;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface OperationLogService {
    void logOperation(OperationLog log);
    List<OperationLog> getAllLogs();
}

