package ru.internship.perepichka.service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.dto.DataForTaskUpdate;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    TaskService taskService;
    EmployeeService employeeService;
    TaskRepository taskRepository;

    private final long defaultId = 1;
    private final Employee defaultEmployee = new Employee(1, "test");
    private final Task defaultTask = Task.builder()
            .id(defaultId)
            .header("header")
            .description("desc")
            .employee(defaultEmployee)
            .deadline(LocalDate.of(2000, 1, 1))
            .build();

    @BeforeEach
    void setUp() {
        employeeService = Mockito.mock(EmployeeService.class);
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskService(taskRepository, employeeService);
    }

    @Nested
    @DisplayName("Testing addTask method")
    class addTaskTest {

        @Test
        void existingTaskId_ExceptionThrown() {
            Mockito.when(taskRepository.existsById(defaultId)).thenReturn(true);
            assertThrows(BadIdException.class, () -> taskService.addTask(defaultTask));
        }

        @Test
        void nonExistingUserId_ExceptionThrown() {
            Mockito.when(taskRepository.existsById(defaultId)).thenReturn(false);
            Mockito.when(employeeService.existsById(defaultId)).thenReturn(false);

            assertThrows(BadIdException.class, () -> taskService.addTask(defaultTask));
        }
    }

    @Nested
    @DisplayName("Testing getTask method")
    class getTaskTest {

        @Test
        void nonExistentTask_ExceptionThrown() {
            Mockito.when(taskRepository.findById(defaultId)).thenReturn(Optional.empty());
            assertThrows(BadIdException.class, () -> taskService.getTask(defaultId));
        }

    }

    @Nested
    @DisplayName("Testing updateTask")
    class updateTaskTest {

        @Test
        void invalidFieldName_ExceptionThrown() {
            DataForTaskUpdate data = new DataForTaskUpdate(defaultId, "wrongField", "newValue");

            Mockito.when(taskRepository.findById(defaultId)).thenReturn(Optional.of(defaultTask));
            assertThrows(BadCommandException.class, () -> taskService.updateTask(data));
        }

        @Test
        void nonExistentEmployeeId_ExceptionThrows() {
            DataForTaskUpdate data = new DataForTaskUpdate(defaultId, "employeeId", "1");
            Mockito.when(taskRepository.findById(defaultId)).thenReturn(Optional.of(defaultTask));
            Mockito.when(employeeService.existsById(defaultId)).thenReturn(false);
            assertThrows(BadIdException.class, () -> taskService.updateTask(data));
        }

        @Test
        void invalidId_ExceptionThrown(){
            DataForTaskUpdate data = new DataForTaskUpdate(defaultId, "employeeId", "1q");
            Mockito.when(taskRepository.findById(defaultId)).thenReturn(Optional.of(defaultTask));
            assertThrows(BadIdException.class, () -> taskService.updateTask(data));
        }
    }

    @Nested
    @DisplayName("Testing deleteTask")
    class deleteTaskTest {

        @Test
        void nonExistedTask_ExceptionThrown() {
            Mockito.when(taskRepository.existsById(defaultId)).thenReturn(false);
            assertThrows(BadIdException.class, () -> taskService.deleteTask(defaultId));
        }
    }
}