package ru.internship.perepichka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.dto.DataForTaskUpdate;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.types.TaskServiceCommandType;
import ru.internship.perepichka.util.CommandParser;
import ru.internship.perepichka.util.DataParser;


@Component
@RequiredArgsConstructor
public class TaskServiceFacade implements ServiceFacade {
    private final TaskService taskService;

    @Override
    public String process(String commandStr, String args) {
        TaskServiceCommandType command = TaskServiceCommandType.typeByUserCommand(commandStr);
        return switch (command) {
            case ADD_TASK -> addTask(args);
            case GET_TASK -> getTask(args);
            case UPDATE_TASK -> updateTask(args);
            case DELETE_TASK -> deleteTask(args);
        };
    }

    private String addTask(String args) {
        Task task = DataParser.parseTaskLine(args);
        taskService.addTask(task);
        return "Task was added successfully";
    }

    private String getTask(String id) {
        return taskService.getTask(id).toString();
    }

    private String updateTask(String args){
        DataForTaskUpdate data = CommandParser.parseUpdateCommand(args);
        taskService.updateTask(data);
        return "Task was updated successfully";
    }

    private String deleteTask(String id){
        taskService.deleteTask(id);
        return "Task was deleted successfully";
    }
}
