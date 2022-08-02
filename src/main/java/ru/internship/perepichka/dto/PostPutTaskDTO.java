package ru.internship.perepichka.dto;

import lombok.Getter;
import lombok.Setter;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class PostPutTaskDTO {

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @NotEmpty
    private String header;

    @NotEmpty
    private String description;

    @NotEmpty
    private String deadline;
    @NotEmpty
    private String status;

    @NotEmpty
    private String employee;

    public LocalDate convertStringToDeadline() {
        try {
            return LocalDate.parse(deadline.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new BadCommandException("Wrong deadline data: Follow date pattern: " + DATE_FORMAT);
        }
    }

    public void convertDeadlineToString(LocalDate date) {
        this.deadline = date.format(formatter);
    }

    public Task.Status convertStringToStatus() {
        for (Task.Status st : Task.Status.values()) {
            if (st.name().equals(status.toUpperCase())) return st;
        }
        throw new BadCommandException("Wrong task status, options: NEW, IN_PROGRESS, DONE");
    }

    public void convertStatusToString(Task.Status status) {
        this.status = status.name();
    }

    public void convertEmployeeToStringId(Employee employee){
        this.employee = employee.getId();
    }

}
