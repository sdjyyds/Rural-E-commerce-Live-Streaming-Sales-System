package com.sdjyyds.admin.mapper;

import com.sdjyyds.admin.entity.Report;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface ReportMapper {
    void insert(Report report);
    List<Report> findByStatus(String status);
    void updateStatus(Report report);
}
