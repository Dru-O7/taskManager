package com.example.taskManager.service;

import com.example.taskManager.dto.DailyTaskCountResponse;
import com.example.taskManager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public Map<String, Long> getTasksByStatus() {

        List<Object[]> results = taskRepository.countTasksByStatus();

        Map<String, Long> response = new HashMap<>();

        for (Object[] row : results) {
            response.put(
                    row[0].toString(),
                    (Long) row[1]
            );
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<DailyTaskCountResponse> getDailyTaskCount() {

        List<Object[]> results = taskRepository.countTasksPerDay();

        return results.stream()
                .map(row -> {
                    java.sql.Date sqlDate = (java.sql.Date) row[0];
                    Long count = (Long) row[1];

                    return new DailyTaskCountResponse(
                            sqlDate.toLocalDate(),
                            count
                    );
                })
                .toList();
    }
}