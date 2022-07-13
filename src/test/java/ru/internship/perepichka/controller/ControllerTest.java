package ru.internship.perepichka.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.service.EmployeeServiceFacade;
import ru.internship.perepichka.service.TaskServiceFacade;


class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() {
        EmployeeServiceFacade employeeServiceFacade = Mockito.mock(EmployeeServiceFacade.class);
        TaskServiceFacade taskServiceFacade = Mockito.mock(TaskServiceFacade.class);
        controller = new Controller(employeeServiceFacade, taskServiceFacade);
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