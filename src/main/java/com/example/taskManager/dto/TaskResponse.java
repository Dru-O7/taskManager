package com.example.taskManager.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class TaskResponse {
    private Long id;
    private String taskNumber;
    private String title;
    private String description;
    private String status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
