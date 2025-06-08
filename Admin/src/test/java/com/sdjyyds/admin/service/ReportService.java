package com.sdjyyds.admin.service;

import com.sdjyyds.admin.entity.Report;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface ReportService {
    void createReport(Report report);
    List<Report> getPendingReports();
    void processReport(Report report);
}

