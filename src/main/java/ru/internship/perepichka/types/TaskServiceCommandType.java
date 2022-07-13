package ru.internship.perepichka.types;

import java.util.HashMap;
import java.util.Map;

public enum TaskServiceCommandType {
    ADD_TASK("addTask"),
    GET_TASK("getTask"),
    UPDATE_TASK("updateTask"),
    DELETE_TASK("deleteTask");

    private static final Map<String, TaskServiceCommandType> BY_LABEL = new HashMap<>();
    private final String userCommand;

    static {
        for (TaskServiceCommandType type : values()) {
            BY_LABEL.put(type.getCommand(), type);
        }
    }

    TaskServiceCommandType(String userCommand) {
        this.userCommand = userCommand;
    }

    public String getCommand() {
        return userCommand;
    }

    public static TaskServiceCommandType typeByUserCommand(String stringCommand) {
        return BY_LABEL.get(stringCommand);
    }
}
