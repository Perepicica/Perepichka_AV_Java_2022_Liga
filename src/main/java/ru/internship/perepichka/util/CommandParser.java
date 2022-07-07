package ru.internship.perepichka.util;

import lombok.experimental.UtilityClass;
import ru.internship.perepichka.exception.BadCommandException;

@UtilityClass
public class CommandParser {

    private static final String UPDATE_COMMAND_ERROR = "Wrong format for update, try updateTask:taskId,fieldName=newValue";

    public static DataForTaskUpdate parseUpdateCommand(String args) {
        DataForTaskUpdate data = new DataForTaskUpdate();
        String[] idAndField = args.split(",");
        if (idAndField.length != 2) {
            throw new BadCommandException(UPDATE_COMMAND_ERROR);
        }
        data.setTaskId(idAndField[0]);
        String[] updateArg = idAndField[1].split("=");
        if (updateArg.length != 2) {
            throw new BadCommandException(UPDATE_COMMAND_ERROR);
        }
        data.setFieldName(updateArg[0]);
        data.setNewValue(updateArg[1]);
        return data;
    }
}
