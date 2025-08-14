package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "The task name cannot be empty.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "The task description cannot be empty")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "The task status is required")
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    @Column(nullable = false)
    @NotNull(message = "Specify a deadline")
    private LocalDate deadline;
}
