package ru.internship.perepichka.dto;

import lombok.Getter;
import lombok.Setter;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class GetTaskDTO {

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private String id;
    private String header;
    private String description;
    private String deadline;
    private String status;
    private String employee;

    public void convertDeadlineToString(LocalDate date) {
        this.deadline = date.format(formatter);
    }

    public void convertStatusToString(Task.Status status) {
        this.status = status.name();
    }

    public void convertEmployeeToStringId(Employee employee){
        this.employee = employee.getId();
    }
}
