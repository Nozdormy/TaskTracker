package com.example.repository;

import com.example.model.Task;
import com.example.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findAllByOrderByDeadlineAsc();
    List<Task> findAllByOrderByStatusAsc();
}
