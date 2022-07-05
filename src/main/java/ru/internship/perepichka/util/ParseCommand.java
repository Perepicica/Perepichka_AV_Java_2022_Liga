package ru.internship.perepichka.util;

import ru.internship.perepichka.exception.BadCommandException;

public class ParseCommand {

    private ParseCommand(){}

    private static final String UPDATE_COMMAND_ERROR = "Wrong format for update, try updateTask:taskId,fieldName=newValue";
    public static String[] parseUpdateCommand(String args) {
        String[] result = new String[3];
        String[] idAndField = args.split(",");
        if (idAndField.length != 2) {
            throw new BadCommandException(UPDATE_COMMAND_ERROR);
        }
        result[0] = idAndField[0];
        String[] updateArg = idAndField[1].split("=");
        if (updateArg.length != 2) {
            throw new BadCommandException(UPDATE_COMMAND_ERROR);
        }
        result[1] = updateArg[0];
        result[2] = updateArg[1];
        if(!checkCommandParts(result)){
            throw new BadCommandException(UPDATE_COMMAND_ERROR);
        }
        return result;
    }

    private static boolean checkCommandParts(String[] parts) {
        for (String part : parts) {
            if (part.trim().length() == 0) {
                return false;
            }
        }
        return true;
    }
}
