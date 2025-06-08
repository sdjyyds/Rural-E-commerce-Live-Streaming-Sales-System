package com.sdjyyds.admin.controller;

import com.sdjyyds.admin.entity.Report;
import com.sdjyyds.admin.service.ReportService;
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
@RequestMapping("/admin/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody Report report) {
        reportService.createReport(report);
        return ResponseEntity.ok("Report submitted.");
    }

    @GetMapping("/pending")
    public List<Report> getPendingReports() {
        return reportService.getPendingReports();
    }

    @PostMapping("/process")
    public ResponseEntity<?> processReport(@RequestBody Report report) {
        reportService.processReport(report);
        return ResponseEntity.ok("Report processed.");
    }
}
