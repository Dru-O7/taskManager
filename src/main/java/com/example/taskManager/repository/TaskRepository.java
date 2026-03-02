package com.example.taskManager.repository;

import com.example.taskManager.entity.Task;
import com.example.taskManager.entity.TaskStatus;
import com.example.taskManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // For USER: get only their tasks
    List<Task> findByCreatedBy(User user);

    // For analytics: count by status
    long countByStatus(TaskStatus status);

}