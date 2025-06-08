package com.sdjyyds.admin.service;

import com.sdjyyds.admin.entity.OperationLog;
import com.sdjyyds.admin.mapper.OperationLogMapper;
import com.sdjyyds.admin.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    private OperationLogMapper operationLogMapper;
    @Autowired
    private SnowflakeIdGenerator idGenerator;
    @Override
    public void logOperation(OperationLog log) {
        log.setId(idGenerator.nextId());
        log.setCreatedAt(new Date());
        operationLogMapper.insert(log);
    }

    @Override
    public List<OperationLog> getAllLogs() {
        return operationLogMapper.selectAll();
    }
}
