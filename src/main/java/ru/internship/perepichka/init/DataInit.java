package ru.internship.perepichka.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.dao.TaskRepository;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.DataLoadingException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataInit implements ApplicationRunner {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    @Value("${employeeData}")
    private String employeeDataFile;
    @Value("${taskData}")
    private String taskDataFile;
    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

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
            String line;
            int pointer = 1;
            switch (type) {
                case EMPLOYEES:
                    while ((line = bufferedReader.readLine()) != null) {
                        employeeRepository.save(parseEmployeeLine(line,pointer));
                        pointer++;
                    }
                    break;
                case TASKS:
                    while ((line = bufferedReader.readLine()) != null) {
                        taskRepository.save(parseTaskLine(line, pointer));
                        pointer++;
                    }
                    break;
            }
        }
    }

    private Employee parseEmployeeLine(String line, int pointer) throws IllegalArgumentException {
        String[] lineParts = line.split(",");
        if (lineParts.length != 2 || lineParts[1].trim().length() == 0) {
            throw new DataLoadingException("Wrong data: user data storage, line " + pointer + " : " + line);
        }

        Long userId = parseId(lineParts[0], pointer, line);
        if (employeeRepository.existsById(userId)) {
            throw new DataLoadingException("Wrong data: multiply user id, line " + pointer + " : " + line);
        }

        Employee employee = new Employee();
        employee.setId(userId);
        employee.setName(lineParts[1].trim());
        return employee;
    }

    public Task parseTaskLine(String line, int pointer) {
        String[] lineParts = line.split(",");
        if (lineParts.length < 5 || lineParts.length > 6 || lineParts[1].trim().length() == 0 || lineParts[2].trim().length() == 0) {
            throw new DataLoadingException("Wrong data: task data storage, line " + pointer + " : " + line);
        }

        Long taskId = parseId(lineParts[0], pointer, line);
        Long userId = parseId(lineParts[3], pointer, line);

        Date deadLine;
        try {
            deadLine = dateFormat.parse(lineParts[4].trim());
        } catch (ParseException e) {
            throw new DataLoadingException("Wrong data: follow data pattern, line " + pointer + " : " + line);
        }

        if (!employeeRepository.existsById(userId)) {
            throw new DataLoadingException("Wrong data: no user with such id, line " + pointer + " : " + line);
        }
        Employee employee = employeeRepository.getReferenceById(userId);

        Task.Status status = Task.Status.NEW;
        if (lineParts.length == 6) status = getStatus(lineParts[5], pointer, line);

        return new Task(taskId, lineParts[1].trim(), lineParts[2].trim(), deadLine, employee, status);
    }

    private static Long parseId(String idStr, int pointer, String line) {
        try {
            return Long.parseLong(idStr.trim());
        } catch (NumberFormatException e) {
            throw new DataLoadingException("Wrong data: id should be Long type, line " + pointer + " : " + line);
        }
    }

    private static Task.Status getStatus(String status, int pointer, String line) {
        for (Task.Status st : Task.Status.values()) {
            if (st.name().equals(status)) return st;
        }
        throw new DataLoadingException("Wrong id: Data storage, line " + pointer + " : " + line);
    }


    public enum FileType {
        EMPLOYEES, TASKS
    }
}
