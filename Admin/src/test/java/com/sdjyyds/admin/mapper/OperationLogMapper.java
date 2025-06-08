package com.sdjyyds.admin.mapper;

import com.sdjyyds.admin.entity.OperationLog;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface OperationLogMapper {
    void insert(OperationLog log);
    List<OperationLog> selectAll();
}