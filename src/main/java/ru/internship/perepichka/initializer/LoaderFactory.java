package ru.internship.perepichka.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.dao.TaskRepository;

@Component
@RequiredArgsConstructor
public class LoaderFactory {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public DataLoader getFactory(FileType type) {
        return switch (type) {
            case EMPLOYEES -> new EmployeeLoader(employeeRepository);
            case TASKS -> new TaskLoader(employeeRepository, taskRepository);
        };
    }
}
