package ru.internship.perepichka.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.internship.perepichka.exception.BadIdException;


class TaskServiceFacadeTest {
    TaskServiceFacade taskServiceFacade;
    TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = Mockito.mock(TaskService.class);
        taskServiceFacade = new TaskServiceFacade(taskService);
    }

    @Test
    void AddTask_ConfirmationReturn() {
        Assertions.assertEquals("Task was added successfully",
                taskServiceFacade.process("addTask", "1,header,desc,1,1.01.2000"));
    }

    @ParameterizedTest
    @CsvSource({"getTask,1q",
            "deleteTask,1q"})
    void invalidId_ExceptionThrown(String command, String arg) {
        Assertions.assertThrows(BadIdException.class, () -> taskServiceFacade.process(command, arg));
    }

    @Test
    void getTask_ConfirmationReturn() {
        Assertions.assertEquals("Task was updated successfully",
                taskServiceFacade.process("updateTask", "1,status=NEW"));
    }

    @Test
    void deleteTask_ConfirmationReturn() {
        Assertions.assertEquals("Task was deleted successfully",
                taskServiceFacade.process("deleteTask", "1"));
    }
}