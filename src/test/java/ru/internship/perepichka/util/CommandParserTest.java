package ru.internship.perepichka.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.internship.perepichka.dto.DataForTaskUpdate;
import ru.internship.perepichka.exception.BadCommandException;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "1",
            "1,header",
            "1:header",
            "1:header=",
            "1,header:newValue"})
    void parseUpdateCommand_ExceptionThrown(String command) {
        assertThrows(BadCommandException.class, () -> CommandParser.parseUpdateCommand(command));
    }

    @Test
    void parseUpdateCommand_SuccessfulParse() {
        DataForTaskUpdate data = CommandParser.parseUpdateCommand("1,header=newHeader");
        Assertions.assertEquals("1", data.getTaskId());
        Assertions.assertEquals("header", data.getFieldName());
        Assertions.assertEquals("newHeader", data.getNewValue());
    }

}