package ru.internship.perepichka.util;

import lombok.experimental.UtilityClass;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DataParser {

    private final int taskLinePartsCount = 5;
    private final int taskHeaderIndex = 0;
    private final int taskDescIndex = 1;
    private final int userIdInTaskIndex = 2;
    private final int taskDeadLineIndex = 3;
    private final int taskStatusIndex = 4;

    public static Task parseTaskLine(String line) {
        String[] lineParts = line.split(",");
        checkTaskLineFormat(lineParts);

        String header = lineParts[taskHeaderIndex].trim();
        String description = lineParts[taskDescIndex].trim();
        String userId = lineParts[userIdInTaskIndex];
        LocalDate deadLine = getDeadLine(lineParts[taskDeadLineIndex]);
        Task.Status status = getStatus(lineParts);
        return new Task(header,description,deadLine,new Employee(userId,"unknown"),status);
    }

    public static Task.Status getStatus(String[] lineParts) {
        if (lineParts.length == taskLinePartsCount) {
            return getStatusType(lineParts[taskStatusIndex]);
        }
        return Task.Status.NEW;
    }

    public static Task.Status getStatusType(String status) {
        for (Task.Status st : Task.Status.values()) {
            if (st.name().equals(status.toUpperCase())) return st;
        }
        throw new BadCommandException("Wrong task status, options: NEW, IN_PROGRESS, DONE");
    }

    public static LocalDate getDeadLine(String date) {
        String dateFormat = "d.MM.yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        try {
            return LocalDate.parse(date.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new BadCommandException("Wrong data: Follow date pattern: " + dateFormat);
        }
    }

    private static void checkTaskLineFormat(String[] lineParts) {
        if (lineParts.length < taskLinePartsCount - 1
                || lineParts.length > taskLinePartsCount
                || lineParts[taskHeaderIndex].trim().length() == 0
                || lineParts[taskDescIndex].trim().length() == 0) {
            throw new BadCommandException("Wrong data: Follow the pattern taskId,header,description,userId,deadLine,status(?)");
        }
    }

}
