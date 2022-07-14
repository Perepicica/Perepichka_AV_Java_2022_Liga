package ru.internship.perepichka.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.internship.perepichka.exception.BadIdException;


class EmployeeServiceFacadeTest {

    EmployeeService employeeService;
    EmployeeServiceFacade employeeServiceFacade;

    @BeforeEach
    void setUp() {
        employeeService = Mockito.mock(EmployeeService.class);
        employeeServiceFacade = new EmployeeServiceFacade(employeeService);
    }

    @Test
    void invalidId_ExceptionThrown() {
        String command = "getEmployeeTasks";
        String invalidId = "1q";
        Assertions.assertThrows(BadIdException.class, () -> employeeServiceFacade.process(command,invalidId));
    }

    @Test
    @DisplayName("Testing deleteAll method")
    void deleteAll_ConfirmationReturn() {
        String confirmation = "All data was deleted successfully";
        Assertions.assertEquals(confirmation, employeeServiceFacade.process("deleteAll",""));
    }

}