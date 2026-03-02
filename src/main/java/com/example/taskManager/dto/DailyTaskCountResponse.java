package com.example.taskManager.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyTaskCountResponse {
    private LocalDate date;
    private Long count;
}
