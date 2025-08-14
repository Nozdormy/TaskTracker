package com.example.service;

import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task addTask(Task task) {
        return repository.save(task);
    }

    @Override
    public Task editTask(Task task) {
        Optional<Task> existing = repository.findById(task.getId());
        if (existing.isEmpty()) {
            throw new TaskNotFoundException(task.getId());
        }

        Task updated = existing.get();
        updated.setName(task.getName());
        updated.setDescription(task.getDescription());
        updated.setStatus(task.getStatus());
        updated.setDeadline(task.getDeadline());

        return repository.save(updated);
    }

    @Override
    public List<Task> filterTasksByStatus(TaskStatus status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<Task> sortTasksByDeadline() {
        return repository.findAllByOrderByDeadlineAsc();
    }

    @Override
    public List<Task> sortTasksByStatus() {
        return repository.findAllByOrderByStatusAsc();
    }

    @Override
    public Optional<Task> findTaskById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }
}
