package service;

import com.example.exception.TaskNotFoundException;
import com.example.model.Task;
import com.example.model.TaskStatus;
import com.example.repository.TaskRepository;
import com.example.service.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceImpl service;

    @Test
    void addTask_ShouldReturnSavedTask() {
        Task task = createTask(null, "New Task", TaskStatus.TODO);
        Task savedTask = createTask(1L, "New Task", TaskStatus.TODO);

        when(repository.save(task)).thenReturn(savedTask);

        Task result = service.addTask(task);

        assertEquals(savedTask, result);
        verify(repository, times(1)).save(task);
    }

    @Test
    void editTask_ShouldUpdateExistingTask() {
        Task existing = createTask(1L, "Old Task", TaskStatus.TODO);
        Task updated = createTask(1L, "Updated Task", TaskStatus.DONE);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(updated);

        Task result = service.editTask(updated);

        assertAll(
                () -> assertEquals(updated.getName(), result.getName()),
                () -> assertEquals(updated.getStatus(), result.getStatus())
        );
        verify(repository).findById(1L);
        verify(repository).save(existing);
    }

    @Test
    void editTask_ShouldThrow_WhenTaskNotFound() {
        Task task = createTask(1L, "Non-existing", TaskStatus.TODO);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> service.editTask(task));
    }

    @Test
    void deleteTask_ShouldCallRepository() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteTask(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deleteTask_ShouldThrow_WhenTaskNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> service.deleteTask(1L));
    }

    @Test
    void findTaskById_ShouldReturnTask() {
        Task task = createTask(1L, "Task", TaskStatus.TODO);

        when(repository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = service.findTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals(task, result.get());
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        List<Task> tasks = createTaskList();
        when(repository.findAll()).thenReturn(tasks);

        List<Task> result = service.getAllTasks();

        assertEquals(tasks.size(), result.size());
        assertEquals(tasks, result);
    }

    @Test
    void filterTasksByStatus_ShouldReturnFilteredTasks() {
        List<Task> tasks = createTaskList();
        when(repository.findAllByStatus(TaskStatus.TODO)).thenReturn(List.of(tasks.get(0)));

        List<Task> result = service.filterTasksByStatus(TaskStatus.TODO);

        assertEquals(1, result.size());
        assertEquals(TaskStatus.TODO, result.get(0).getStatus());
    }

    @Test
    void sortTasksByDeadline_ShouldReturnSortedTasks() {
        List<Task> tasks = createTaskList();
        when(repository.findAllByOrderByDeadlineAsc()).thenReturn(tasks);

        List<Task> result = service.sortTasksByDeadline();

        assertEquals(tasks, result);
    }

    @Test
    void sortTasksByStatus_ShouldReturnSortedTasks() {
        List<Task> tasks = createTaskList();
        when(repository.findAllByOrderByStatusAsc()).thenReturn(tasks);

        List<Task> result = service.sortTasksByStatus();

        assertEquals(tasks, result);
    }

    private Task createTask(Long id, String name, TaskStatus status) {
        return Task.builder()
                .id(id)
                .name(name)
                .description("Test description")
                .status(status)
                .deadline(LocalDate.now().plusDays(1))
                .build();
    }

    private List<Task> createTaskList() {
        return List.of(
                createTask(1L, "Task 1", TaskStatus.TODO),
                createTask(2L, "Task 2", TaskStatus.IN_PROGRESS),
                createTask(3L, "Task 3", TaskStatus.DONE)
        );
    }
}
