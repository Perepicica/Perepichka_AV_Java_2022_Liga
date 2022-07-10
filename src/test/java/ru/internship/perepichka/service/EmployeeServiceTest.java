package ru.internship.perepichka.service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadIdException;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;


class EmployeeServiceTest {

    EmployeeService employeeService;
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Nested
    @DisplayName("Testing getEmployeeTasks method")
    class getEmployeeTasksStringTest {

        @Test
        void invalidId_ExceptionThrown() {
            String invalidId = "1q";
            Assertions.assertThrows(BadIdException.class, () -> employeeService.getEmployeeTasksString(invalidId));
        }

        @Test
        void nonExistentEmployee_ExceptionThrown() {
            String id = "1";
            Mockito.when(employeeRepository.findById(Long.parseLong(id))).thenReturn(Optional.empty());
            Assertions.assertThrows(BadIdException.class, () -> employeeService.getEmployeeTasksString(id));
        }

        @Test
        void success_ReturnTasks() throws NoSuchFieldException, IllegalAccessException {
            Employee employee = getEmployeeWithTasks();
            Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

            String expected = "";
            for (Task task : employee.getTasks()) {
                expected += task.toString();
            }

            Assertions.assertEquals(expected, employeeService.getEmployeeTasksString(employee.getId().toString()));
        }

        Employee getEmployeeWithTasks() throws NoSuchFieldException, IllegalAccessException {
            Employee employee = new Employee(1, "testName");
            LocalDate date = LocalDate.of(2022, 2, 1);
            Task employeeTask1 = new Task(1L, "", "", date, employee, Task.Status.NEW);
            Task employeeTask2 = new Task(2L, "", "", date, employee, Task.Status.NEW);

            Field tasks = employee.getClass().getDeclaredField("tasks");
            tasks.setAccessible(true);
            tasks.set(employee, List.of(employeeTask1, employeeTask2));

            return employee;
        }
    }

    @Test
    @DisplayName("Testing deleteAll method")
    void deleteAll_ConfirmationReturn() {
        String confirmation = "All data was deleted successfully";
        Assertions.assertEquals(confirmation, employeeService.deleteUsers());
    }
}