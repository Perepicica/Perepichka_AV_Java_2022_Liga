package ru.internship.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.dto.TaskFilters;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.types.EmployeeServiceCommandType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeServiceFacade implements ServiceFacade {

    private final EmployeeService employeeService;

    @Override
    public String process(String commandStr, String args) {
        EmployeeServiceCommandType command = EmployeeServiceCommandType.typeByUserCommand(commandStr);
        return switch (command) {
            case GET_EMPLOYEE_TASKS -> getEmployeeTasks(args);
            case DELETE_ALL -> deleteAll();
        };
    }

    private String getEmployeeTasks(String id) {
        List<Task> tasks = employeeService.getEmployeeTasks(id);

        StringBuilder builder = new StringBuilder();
        for (Task task : tasks) {
            builder.append(task.toString());
        }

        return builder.toString();
    }

    private String deleteAll() {
        employeeService.deleteUsers();
        return "All data was deleted successfully";
    }
    public String getEmployeeWithMaxTasks(TaskFilters filters){
         return employeeService.getEmployeeWithMaxTasks(filters);
    }
}