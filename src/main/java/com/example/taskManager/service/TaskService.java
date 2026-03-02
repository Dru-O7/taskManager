package com.example.taskManager.service;

import com.example.taskManager.dto.TaskResponse;
import com.example.taskManager.entity.Role;
import com.example.taskManager.entity.Task;
import com.example.taskManager.entity.TaskStatus;
import com.example.taskManager.entity.User;
import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // ==========================
    // CREATE TASK (USER only)
    // ==========================

    @Transactional
    public TaskResponse createTask(String title, String description){

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCreatedBy(user);
        task.setStatus(TaskStatus.CREATED);

        taskRepository.saveAndFlush(task);

        String taskNumber = "TASK-" +
                String.format("%04d", task.getId());

        task.setTaskNumber(taskNumber);

        taskRepository.save(task);   // explicit second save

        return mapToResponse(task);
    }

    // ==========================
    // GET TASKS
    // ==========================
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        List<Task> tasks;

        if (user.getRole() == Role.ROLE_ADMIN) {
            tasks = taskRepository.findAll();
        } else {
            tasks = taskRepository.findByCreatedBy(user);
        }

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ==========================
    // APPROVE
    // ==========================
    @Transactional
    public TaskResponse approveTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        if (task.getStatus() != TaskStatus.CREATED) {
            throw new IllegalStateException("Task already finalized");
        }

        task.setStatus(TaskStatus.APPROVED);

        return mapToResponse(task);
    }

    // ==========================
    // REJECT
    // ==========================
    @Transactional
    public TaskResponse rejectTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        if (task.getStatus() != TaskStatus.CREATED) {
            throw new IllegalStateException("Task already finalized");
        }

        task.setStatus(TaskStatus.REJECTED);

        return mapToResponse(task);
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .taskNumber(task.getTaskNumber())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .createdBy(task.getCreatedBy().getUsername())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}