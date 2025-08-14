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
        Task updated = repository.findById(task.getId())
                .orElseThrow(() -> new TaskNotFoundException(task.getId()));

        updated.setName(task.getName());
        updated.setDescription(task.getDescription());
        updated.setStatus(task.getStatus());
        updated.setDeadline(task.getDeadline());

        return repository.save(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> filterTasksByStatus(TaskStatus status) {
        return repository.findAllByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> sortTasksByDeadline() {
        return repository.findAllByOrderByDeadlineAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> sortTasksByStatus() {
        return repository.findAllByOrderByStatusAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findTaskById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public void deleteTask(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
