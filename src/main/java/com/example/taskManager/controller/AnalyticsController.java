package com.example.taskManager.controller;

import com.example.taskManager.dto.DailyTaskCountResponse;
import com.example.taskManager.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/tasks-by-status")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Long> tasksByStatus() {
        return analyticsService.getTasksByStatus();
    }

    @GetMapping("/daily-task-count")
    @PreAuthorize("hasRole('ADMIN')")
    public List<DailyTaskCountResponse> dailyTaskCount() {
        return analyticsService.getDailyTaskCount();
    }
}