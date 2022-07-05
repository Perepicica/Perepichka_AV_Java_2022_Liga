package ru.internship.perepichka.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.DataLoadingException;
import ru.internship.perepichka.util.ParseData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class DataInit implements ApplicationRunner {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    @Value("${employeeData}")
    private String employeeDataFile;
    @Value("${taskData}")
    private String taskDataFile;

    @Autowired
    public DataInit(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
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
            employeeRepository.save(ParseData.parseEmployeeLine(new DataLoadingException(""), line));
        }
    }

    private void saveTasks(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Task task = ParseData.parseTaskLine(new DataLoadingException(""), line);
            task.setEmployee(employeeRepository.getReferenceById(task.getEmployee().getId()));
            taskRepository.save(task);
        }
    }

    public enum FileType {
        EMPLOYEES, TASKS
    }
}
