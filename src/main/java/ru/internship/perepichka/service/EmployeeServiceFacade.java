package ru.internship.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.types.EmployeeServiceCommandType;

@Component
@RequiredArgsConstructor
public class EmployeeServiceFacade implements ServiceFacade {

    private final EmployeeService employeeService;

    @Override
    public String process(String commandStr, String args) {
        EmployeeServiceCommandType command = EmployeeServiceCommandType.typeByUserCommand(commandStr);
        return switch (command) {
            case GET_EMPLOYEE_TASKS -> employeeService.getEmployeeTasksString(args);
            case DELETE_ALL -> employeeService.deleteUsers();
        };
    }
}