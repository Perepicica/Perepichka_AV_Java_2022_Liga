package ru.internship.perepichka.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.DataLoadingException;
import ru.internship.perepichka.util.DataParser;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    @Value("${file.employees}")
    private String employeeDataFile;
    @Value("${file.tasks}")
    private String taskDataFile;

    @PostConstruct
    public void loadData() throws IOException {
        if (employeeRepository.count() == 0) {
            csvDataLoader(employeeDataFile, FileType.EMPLOYEES);
            csvDataLoader(taskDataFile, FileType.TASKS);
        }
    }

    private void csvDataLoader(String fileName, FileType type) throws IOException {
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if (type == FileType.EMPLOYEES) {
                saveEmployees(bufferedReader);
            }
            if (type == FileType.TASKS) {
                saveTasks(bufferedReader);
            }
        }
    }

    private void saveEmployees(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            employeeRepository.save(DataParser.parseEmployeeLine(new DataLoadingException(""), line));
        }
    }

    private void saveTasks(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Task task = DataParser.parseTaskLine(new DataLoadingException(""), line);
            task.setEmployee(employeeRepository.getReferenceById(task.getEmployee().getId()));
            taskRepository.save(task);
        }
    }

    public enum FileType {
        EMPLOYEES, TASKS
    }
}
