package ru.internship.perepichka.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.service.EmployeeService;
import ru.internship.perepichka.service.TaskService;


class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() {
        EmployeeService employeeService = Mockito.mock(EmployeeService.class);
        TaskService taskService = Mockito.mock(TaskService.class);
        controller = new Controller(employeeService, taskService);
    }

    @Test
    void processCommand_TooFewCommandArgs_ExceptionThrown() {
        Assertions.assertThrows(BadCommandException.class, () -> controller.processCommand("getEmployeeTasks:"));
    }

    @Test
    void processCommand_WrongCommandArg_ExceptionThrown() {
        Assertions.assertThrows(BadCommandException.class, () -> controller.processCommand("getEmployeeName:1"));
    }

}