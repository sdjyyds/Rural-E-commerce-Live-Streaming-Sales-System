package com.sdjyyds.admin.service;

import com.sdjyyds.admin.entity.Report;
import com.sdjyyds.admin.mapper.ReportMapper;
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
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private SnowflakeIdGenerator idGenerator;
    @Override
    public void createReport(Report report) {
        report.setId(idGenerator.nextId());
        report.setCreatedAt(new Date());
        report.setUpdatedAt(new Date());
        report.setStatus("PENDING");
        reportMapper.insert(report);
    }

    @Override
    public List<Report> getPendingReports() {
        return reportMapper.findByStatus("PENDING");
    }

    @Override
    public void processReport(Report report) {
        report.setProcessedAt(new Date());
        report.setUpdatedAt(new Date());
        reportMapper.updateStatus(report);
    }
}

