package com.example.controller;

import com.example.dto.TaskDTO;
import com.example.model.TaskStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskController {
    ResponseEntity<TaskDTO> createTask(TaskDTO taskDTO);

    ResponseEntity<List<TaskDTO>> getAllTasks();

    ResponseEntity<TaskDTO> getTaskById(Long id);

    ResponseEntity<TaskDTO> editTask(Long id, TaskDTO taskDTO);

    ResponseEntity<Void> deleteTask(Long id);

    ResponseEntity<List<TaskDTO>> filterTasksByStatus(TaskStatus status);

    ResponseEntity<List<TaskDTO>> sortTasksByDeadline();

    ResponseEntity<List<TaskDTO>> sortTasksByStatus();
}
