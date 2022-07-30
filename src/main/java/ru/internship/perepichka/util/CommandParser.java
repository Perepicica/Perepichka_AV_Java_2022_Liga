package ru.internship.perepichka.util;

import lombok.experimental.UtilityClass;
import ru.internship.perepichka.dto.DataForControllerProcessing;
import ru.internship.perepichka.dto.DataForTaskUpdate;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.service.EmployeeServiceFacade;
import ru.internship.perepichka.service.ServiceFacade;
import ru.internship.perepichka.service.TaskServiceFacade;
import ru.internship.perepichka.types.EmployeeServiceCommandType;
import ru.internship.perepichka.types.TaskServiceCommandType;

@UtilityClass
public class CommandParser {

    private static final String UPDATE_COMMAND_ERROR = "Wrong format for update, try updateTask:taskId,fieldName=newValue";

    private final String idAndUpdateArgDelimiter = ",";
    private final String fieldAndValueDelimiter = "=";
    private final String commandAndArgsDelimiter = ":";

    private final int splitPartsCount = 2;

    private final int taskId = 0;
    private final int infoToUpdate = 1;
    private final int fieldName = 0;
    private final int newValue = 1;

    private final int commandName = 0;
    private final int commandArgs = 1;

    public static DataForTaskUpdate parseUpdateCommand(String args) {
        DataForTaskUpdate data = new DataForTaskUpdate();

        String[] idAndField = args.split(idAndUpdateArgDelimiter);
        if (idAndField.length != splitPartsCount) {
            throw new BadCommandException(UPDATE_COMMAND_ERROR);
        }
        data.setTaskId(idAndField[taskId]);

        String[] updateArg = idAndField[infoToUpdate].split(fieldAndValueDelimiter);
        if (updateArg.length != splitPartsCount) {
            throw new BadCommandException(UPDATE_COMMAND_ERROR);
        }

        data.setFieldName(updateArg[fieldName]);
        data.setNewValue(updateArg[newValue]);

        return data;
    }

    public DataForControllerProcessing parseControllerCommand(String command) {
        String[] requestParts = command.split(commandAndArgsDelimiter);
        checkCommandLineFormat(requestParts);

        DataForControllerProcessing data = new DataForControllerProcessing();
        data.setCommand(requestParts[commandName]);
        data.setArgs(requestParts[commandArgs]);

        Class<? extends ServiceFacade> className = determineCommandType(data.getCommand());
        data.setClassName(className);

        return data;
    }

    private void checkCommandLineFormat(String[] requestParts){
        if (requestParts.length != splitPartsCount) {
            throw new BadCommandException("Follow the command pattern command:arg1,arg2...");
        }
    }

    private Class<? extends ServiceFacade> determineCommandType(String command) {
        if (EmployeeServiceCommandType.typeByUserCommand(command) != null) {
            return EmployeeServiceFacade.class;
        }
        if (TaskServiceCommandType.typeByUserCommand(command) != null){
            return TaskServiceFacade.class;
        }
        throw new BadCommandException("No such command");
    }
}
