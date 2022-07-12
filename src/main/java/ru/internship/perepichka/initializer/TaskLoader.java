package ru.internship.perepichka.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.DataLoadingException;
import ru.internship.perepichka.util.DataParser;

import java.io.BufferedReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TaskLoader implements DataLoader {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    @Override
    public void saveData(BufferedReader br) throws IOException {
        if (taskRepository.count() != 0) return;
        String line;
        while ((line = br.readLine()) != null) {
            Task task = DataParser.parseTaskLine(new DataLoadingException(""), line);
            task.setEmployee(employeeRepository.getReferenceById(task.getEmployee().getId()));
            taskRepository.save(task);
        }
    }
}
