package com.example.service;

import com.example.model.Task;
import com.example.model.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task addTask(Task task);

    Task editTask(Task task);

    List<Task> filterTasksByStatus(TaskStatus status);

    List<Task> sortTasksByDeadline();

    List<Task> sortTasksByStatus();

    Optional<Task> findTaskById(long id);

    List<Task> getAllTasks();

    void deleteTask(Long id);
}
