package com.example.dto;

import com.example.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;

    @NotBlank(message = "The task name cannot be empty.")
    private String name;

    @NotBlank(message = "The task description cannot be empty")
    private String description;

    @NotNull(message = "The task status is required")
    private TaskStatus status;

    @NotNull(message = "Specify a deadline")
    private LocalDate deadline;
}
