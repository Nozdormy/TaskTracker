package com.example.controller;

import com.example.dto.TaskDTO;
import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskStatus;
import com.example.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Override
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        Task savedTask = taskService.addTask(task);

        return ResponseEntity.ok(convertToDTO(savedTask));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(mapTasksToDTO(taskService.getAllTasks()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Task task = taskService.findTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return ResponseEntity.ok(convertToDTO(task));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> editTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        task.setId(id);
        Task updatedTask = taskService.editTask(task);

        return ResponseEntity.ok(convertToDTO(updatedTask));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.findTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/filter")
    public ResponseEntity<List<TaskDTO>> filterTasksByStatus(@RequestParam TaskStatus status) {
        return ResponseEntity.ok(mapTasksToDTO(taskService.filterTasksByStatus(status)));
    }

    @Override
    @GetMapping("/sort/deadline")
    public ResponseEntity<List<TaskDTO>> sortTasksByDeadline() {
        return ResponseEntity.ok(mapTasksToDTO(taskService.sortTasksByDeadline()));
    }

    @Override
    @GetMapping("/sort/status")
    public ResponseEntity<List<TaskDTO>> sortTasksByStatus() {
        return ResponseEntity.ok(mapTasksToDTO(taskService.sortTasksByStatus()));
    }

    private TaskDTO convertToDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private List<TaskDTO> mapTasksToDTO(List<Task> tasks) {
        return tasks.stream()
                .map(this::convertToDTO)
                .toList();
    }
}