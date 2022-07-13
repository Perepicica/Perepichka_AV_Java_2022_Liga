package ru.internship.perepichka.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.internship.perepichka.dao.TaskRepository;
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
    private final String defaultStringId = "1";
    private final String defaultInvalidId = "1q";
    private final String defaultTaskLine = "1,header,desc,1,1.01.2000";
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
            assertThrows(BadIdException.class, () -> taskService.addTask(defaultTaskLine));
        }

        @Test
        void nonExistingUserId_ExceptionThrown() {
            Mockito.when(taskRepository.existsById(defaultId)).thenReturn(false);
            Mockito.when(employeeService.existsById(defaultId)).thenReturn(false);

            assertThrows(BadIdException.class, () -> taskService.addTask(defaultTaskLine));
        }

        @Test
        void successfullyAdding_ConfirmationReturn() {
            Mockito.when(taskRepository.existsById(defaultId)).thenReturn(false);
            Mockito.when(employeeService.existsById(defaultId)).thenReturn(true);
            Mockito.when(employeeService.getReferenceById(defaultId)).thenReturn(defaultEmployee);
            Mockito.when(taskRepository.save(defaultTask)).thenReturn(null);

            Assertions.assertEquals("Task was added successfully", taskService.addTask(defaultTaskLine));
        }
    }

    @Nested
    @DisplayName("Testing getTask method")
    class getTaskTest {
        @Test
        void invalidId_ExceptionThrown() {
            assertThrows(BadIdException.class, () -> taskService.getTaskString(defaultInvalidId));
        }

        @Test
        void nonExistentTask_ExceptionThrown() {
            Mockito.when(taskRepository.findById(defaultId)).thenReturn(Optional.empty());
            assertThrows(BadIdException.class, () -> taskService.getTaskString(defaultStringId));
        }

        @Test
        void successfullyGettingTask_ConfirmationReturn() {
            Mockito.when(taskRepository.findById(defaultId)).thenReturn(Optional.of(defaultTask));
            Assertions.assertEquals(defaultTask.toString(), taskService.getTaskString(defaultStringId));
        }
    }

    @Nested
    @DisplayName("Testing updateTask")
    class updateTaskTest {

        @Test
        void invalidFieldName_ExceptionThrown() {
            Mockito.when(taskRepository.findById(defaultId)).thenReturn(Optional.of(defaultTask));
            assertThrows(BadCommandException.class, () -> taskService.updateTask(defaultStringId + ",wrongField=newValue"));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                defaultStringId + ",employeeId=" + defaultInvalidId,
                defaultStringId + ",employeeId=" + defaultId})
        void invalidEmployeeId_ExceptionThrows(String command) {
            Mockito.when(taskRepository.findById(defaultId)).thenReturn(Optional.of(defaultTask));
            Mockito.when(employeeService.existsById(defaultId)).thenReturn(false);
            assertThrows(BadIdException.class, () -> taskService.updateTask(command));
        }

        @Test
        void successfullyUpdatingTask_ConfirmationReturn() {
            Mockito.when(taskRepository.findById(defaultId)).thenReturn(Optional.of(defaultTask));
            Assertions.assertEquals("Task was updated successfully", taskService.updateTask("1,status=new"));
        }
    }

    @Nested
    @DisplayName("Testing deleteTask")
    class deleteTaskTest {
        @Test
        void invalidId_ExceptionThrown() {
            assertThrows(BadIdException.class, () -> taskService.deleteTask(defaultInvalidId));
        }

        @Test
        void nonExistedTask_ExceptionThrown() {
            Mockito.when(taskRepository.existsById(defaultId)).thenReturn(false);
            assertThrows(BadIdException.class, () -> taskService.deleteTask(defaultStringId));
        }

        @Test
        void successfullyDeletingTask_ConfirmationReturn() {
            Mockito.when(taskRepository.existsById(defaultId)).thenReturn(true);
            Assertions.assertEquals("Task was deleted successfully",
                    taskService.deleteTask(defaultStringId));
        }
    }
}