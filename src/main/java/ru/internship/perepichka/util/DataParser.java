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

    public static long parseId(Exception exc, String... args) {
        try {
            return Long.parseLong(args[0].trim());
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

        long userId = parseId(exc, lineParts[0], line);
        String name = lineParts[1].trim();

        return new Employee(userId, name);
    }

    public static Task parseTaskLine(Exception exc, String line) {
        String[] lineParts = line.split(",");
        checkTaskLineFormat(exc, lineParts, line);

        long taskId = parseId(exc, lineParts[0], line);
        String header = lineParts[1].trim();
        String description = lineParts[2].trim();
        long userId = parseId(exc, lineParts[3], line);
        LocalDate deadLine = getDeadLine(exc, lineParts[4], line);
        Task.Status status = getStatus(exc, lineParts, line);

        return new Task(taskId, header, description, deadLine, new Employee(userId, "unknown"), status);
    }

    public static Task.Status getStatus(Exception exc, String[] lineParts, String line) {
        if (lineParts.length == 6) {
            return getStatusType(exc, lineParts[5], line);
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
        if (lineParts.length != 2 || lineParts[1].trim().length() == 0) {
            throw new DataLoadingException("Wrong data: user data storage, line : " + line);
        }
    }

    private static void checkTaskLineFormat(Exception exc, String[] lineParts, String line) {
        if (lineParts.length < 5 || lineParts.length > 6 || lineParts[1].trim().length() == 0 || lineParts[2].trim().length() == 0) {
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
