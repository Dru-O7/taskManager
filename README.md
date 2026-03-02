# Task Manager API

A role-based Task Management REST API built using Spring Boot, Spring Security (JWT), and MySQL.

## Overview

This application allows users to create tasks and administrators to manage and analyze them. Authentication is handled using JWT, and access is controlled based on user roles.

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT (jjwt)
- Spring Data JPA
- Hibernate
- MySQL

## Features

### Authentication

- User registration
- User login using JWT-based authentication

### Authorization

- Role-based access control (USER and ADMIN)

### Task Management

- USER can create tasks
- USER can view their own tasks
- ADMIN can view all tasks
- ADMIN can approve tasks
- ADMIN can reject tasks
- Task number auto-generated in the format `TASK-0001`

### Analytics (ADMIN only)

- Count of tasks grouped by status
- Daily task count

## Roles

### USER

- Create tasks
- View own tasks

### ADMIN

- View all tasks
- Approve tasks
- Reject tasks
- Access analytics endpoints

## API Endpoints

### Authentication

- POST /auth/register
- POST /auth/login

### Tasks

- POST /tasks
- GET /tasks
- PUT /tasks/{id}/approve
- PUT /tasks/{id}/reject

### Analytics

- GET /analytics/tasks-by-status
- GET /analytics/daily-task-count

## Database Configuration

1. Create a MySQL database named `task_manager`.
2. Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_manager
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update 
```
## Running the Application

Build and run using Maven:

mvn clean install  
mvn spring-boot:run  

The application runs at:

http://localhost:8080

## Implementation Notes

- The application uses stateless authentication with JWT.
- Passwords are encrypted using BCrypt.
- Entities are not exposed directly; responses use DTOs.
- Transactions are used for task creation and updates.
