package ru.internship.perepichka.util;

import lombok.experimental.UtilityClass;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.exception.DataLoadingException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DataParser {

    private final int employeeLinePartsCount = 2;
    private final int taskLinePartsCount = 6;

    private final int idIndex = 0;
    private final int employeeNameIndex = 1;

    private final int taskHeaderIndex = 1;
    private final int taskDescIndex = 2;
    private final int userIdInTaskIndex = 3;
    private final int taskDeadLineIndex = 4;
    private final int taskStatusIndex = 5;

    public static long parseId(Exception exc, String... args) {
        try {
            return Long.parseLong(args[idIndex].trim());
        } catch (NumberFormatException e) {
            if (exc instanceof DataLoadingException) {
                throw new DataLoadingException("Wrong id: Id should be long type, line : " + args[1]);
            } else {
                throw new BadIdException("Id should be long type");
            }
        }
    }

    public static Employee parseEmployeeLine(Exception exc, String line) {
        String[] lineParts = line.split(",");
        checkEmployeeLineFormat(line, lineParts);

        long userId = parseId(exc, lineParts[idIndex], line);
        String name = lineParts[employeeNameIndex].trim();

        return new Employee(userId, name);
    }

    public static Task parseTaskLine(Exception exc, String line) {
        String[] lineParts = line.split(",");
        checkTaskLineFormat(exc, lineParts, line);

        long taskId = parseId(exc, lineParts[idIndex], line);
        String header = lineParts[taskHeaderIndex].trim();
        String description = lineParts[taskDescIndex].trim();
        long userId = parseId(exc, lineParts[userIdInTaskIndex], line);
        LocalDate deadLine = getDeadLine(exc, lineParts[taskDeadLineIndex], line);
        Task.Status status = getStatus(exc, lineParts, line);

        return Task.builder()
                .id(taskId)
                .header(header)
                .description(description)
                .employee(new Employee(userId, "unknown"))
                .deadline(deadLine)
                .status(status).build();
    }

    public static Task.Status getStatus(Exception exc, String[] lineParts, String line) {
        if (lineParts.length == taskLinePartsCount) {
            return getStatusType(exc, lineParts[taskStatusIndex], line);
        }
        return Task.Status.NEW;
    }

    public static Task.Status getStatusType(Exception exc, String status, String line) {
        Task.Status result = Task.Status.NEW;
        for (Task.Status st : Task.Status.values()) {
            if (st.name().equals(status.toUpperCase())) return st;
        }
        throwError(exc, "Wrong task status: Data storage, line : " + line,
                "Wrong task status, options: NEW, IN_PROGRESS, DONE");
        return result;
    }

    public static LocalDate getDeadLine(Exception exc, String date, String line) {
        String dateFormat = "d.MM.yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        try {
            return LocalDate.parse(date.trim(), formatter);
        } catch (DateTimeParseException e) {
            throwError(exc, "Wrong data: Task data storage, line : " + line,
                    "Wrong data: Follow date pattern: " + dateFormat);
        }
        return LocalDate.now();
    }

    private static void checkEmployeeLineFormat(String line, String[] lineParts) {
        if (lineParts.length != employeeLinePartsCount || lineParts[employeeNameIndex].trim().length() == 0) {
            throw new DataLoadingException("Wrong data: user data storage, line : " + line);
        }
    }

    private static void checkTaskLineFormat(Exception exc, String[] lineParts, String line) {
        if (lineParts.length < taskLinePartsCount - 1
                || lineParts.length > taskLinePartsCount
                || lineParts[taskHeaderIndex].trim().length() == 0
                || lineParts[taskDescIndex].trim().length() == 0) {
            throwError(exc, "Wrong data: Task data storage, line: " + line,
                    "Wrong data: Follow the pattern taskId,header,description,userId,deadLine,status(?)");
        }
    }

    private static void throwError(Exception exc, String dataLoadingExMessage, String badCommandMessage) {
        if (exc instanceof DataLoadingException) {
            throw new DataLoadingException(dataLoadingExMessage);
        } else {
            throw new BadCommandException(badCommandMessage);
        }
    }
}
