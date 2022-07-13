package ru.internship.perepichka.types;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeServiceCommandType {
    GET_EMPLOYEE_TASKS("getEmployeeTasks"),
    DELETE_ALL("deleteAll");

    private static final Map<String, EmployeeServiceCommandType> BY_LABEL = new HashMap<>();
    private final String userCommand;

    static {
        for (EmployeeServiceCommandType type : values()) {
            BY_LABEL.put(type.getCommand(), type);
        }
    }

    EmployeeServiceCommandType(String userCommand) {
        this.userCommand = userCommand;
    }

    public String getCommand() {
        return userCommand;
    }

    public static EmployeeServiceCommandType typeByUserCommand(String stringCommand) {
        return BY_LABEL.get(stringCommand);
    }
}