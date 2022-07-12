package ru.internship.perepichka.util;

import lombok.experimental.UtilityClass;
import ru.internship.perepichka.dto.DataForTaskUpdate;
import ru.internship.perepichka.exception.BadCommandException;

@UtilityClass
public class CommandParser {

    private static final String UPDATE_COMMAND_ERROR = "Wrong format for update, try updateTask:taskId,fieldName=newValue";

    private final String idAndUpdateArgDelimiter = ",";
    private final String fieldAndValueDelimiter = "=";

    private final int splitPartsCount = 2;
    private final int taskIdIndex = 0;
    private final int infoToUpdateIndex = 1;
    private final int fieldNameIndex = 0;
    private final int newValueIndex = 1;

    public static DataForTaskUpdate parseUpdateCommand(String args) {
        DataForTaskUpdate data = new DataForTaskUpdate();
        String[] idAndField = args.split(idAndUpdateArgDelimiter);
        if (idAndField.length != splitPartsCount) {
            throw new BadCommandException(UPDATE_COMMAND_ERROR);
        }
        data.setTaskId(idAndField[taskIdIndex]);
        String[] updateArg = idAndField[infoToUpdateIndex].split(fieldAndValueDelimiter);
        if (updateArg.length != splitPartsCount) {
            throw new BadCommandException(UPDATE_COMMAND_ERROR);
        }
        data.setFieldName(updateArg[fieldNameIndex]);
        data.setNewValue(updateArg[newValueIndex]);
        return data;
    }
}
