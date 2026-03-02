package com.example.taskManager.repository;

import com.example.taskManager.entity.Task;
import com.example.taskManager.entity.TaskStatus;
import com.example.taskManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCreatedBy(User user);

    long countByStatus(TaskStatus status);

    @Query("SELECT t.status, COUNT(t) FROM Task t GROUP BY t.status")
    List<Object[]> countTasksByStatus();

    @Query("""
           SELECT FUNCTION('DATE', t.createdAt), COUNT(t)
           FROM Task t
           GROUP BY FUNCTION('DATE', t.createdAt)
           ORDER BY FUNCTION('DATE', t.createdAt)
           """)
    List<Object[]> countTasksPerDay();
}