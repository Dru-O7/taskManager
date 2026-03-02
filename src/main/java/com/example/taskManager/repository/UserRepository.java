package com.example.taskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskManager.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
