package ru.internship.perepichka.dto;

import lombok.Getter;
import lombok.Setter;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class TaskDTO {

    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private String id;
    private String header;
    private String description;
    private String deadline;
    private String status;

    public LocalDate convertStringToDeadline() {
        if (deadline == null) return null;
        try {
            return LocalDate.parse(deadline.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new BadCommandException("Wrong deadline data: Follow date pattern: " + DATE_FORMAT);
        }
    }

    public void convertDeadlineToString(LocalDate date) {
        if (date == null) {
            this.deadline = null;
            return;
        }
        this.deadline = date.format(formatter);
    }

    public Task.Status convertStringToStatus() {
        if (status == null) return null;
        for (Task.Status st : Task.Status.values()) {
            if (st.name().equals(status.toUpperCase())) return st;
        }
        throw new BadCommandException("Wrong task status, options: NEW, IN_PROGRESS, DONE");
    }

    public void convertStatusToString(Task.Status status) {
        if (status == null) {
            this.status = null;
            return;
        }
        this.status = status.name();
    }

}
