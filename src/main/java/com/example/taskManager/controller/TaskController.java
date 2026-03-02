package com.example.taskManager.controller;

import com.example.taskManager.dto.CreateTaskRequest;
import com.example.taskManager.dto.TaskResponse;
import com.example.taskManager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ==========================
    // CREATE TASK (USER only)
    // ==========================
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody CreateTaskRequest request) {

        return ResponseEntity.ok(
                taskService.createTask(
                        request.getTitle(),
                        request.getDescription()
                )
        );
    }

    // ==========================
    // GET TASKS
    // ==========================
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<TaskResponse>> getTasks() {

        return ResponseEntity.ok(taskService.getTasks());
    }

    // ==========================
    // APPROVE (ADMIN only)
    // ==========================
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskResponse> approveTask(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.approveTask(id));
    }

    // ==========================
    // REJECT (ADMIN only)
    // ==========================
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaskResponse> rejectTask(@PathVariable Long id) {

        return ResponseEntity.ok(taskService.rejectTask(id));
    }
}