package ru.internship.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.types.TaskServiceCommandType;

@Component
@RequiredArgsConstructor
public class TaskServiceFacade implements ServiceFacade {
    private final TaskService taskService;

    @Override
    public String process(String commandStr, String args) {
        TaskServiceCommandType command = TaskServiceCommandType.typeByUserCommand(commandStr);
        return switch (command) {
            case ADD_TASK -> taskService.addTask(args);
            case GET_TASK -> taskService.getTaskString(args);
            case UPDATE_TASK -> taskService.updateTask(args);
            case DELETE_TASK -> taskService.deleteTask(args);
        };
    }
}
