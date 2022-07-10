package ru.internship.perepichka.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.service.TaskService;

import static org.junit.jupiter.api.Assertions.assertThrows;


class DataParserTest {

    Exception exceptionTypeFromTaskService = TaskService.exceptionType.apply("");


    @Nested
    @DisplayName("Task data parser testing")
    class parseTaskLineTest {

        @ParameterizedTest
        @ValueSource(strings = {
                "19w,header,description,25.05.2022",
                "19w,header,description,1,25.05.2022,New,somethingAdditional"})
        void wrongArgsNumber_ExceptionThrown(String taskLine) {
            assertThrows(exceptionTypeFromTaskService.getClass(),
                    () -> DataParser.parseTaskLine(exceptionTypeFromTaskService, taskLine));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "19w,header,description,1,25.05.2022",
                "19,header,description,e1,25.05.2022"})
        void invalidId_ExceptionThrown(String newTask) {
            assertThrows(BadIdException.class,
                    () -> DataParser.parseTaskLine(exceptionTypeFromTaskService, newTask));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "19,,description,1,25.05.2022",
                "19,header,,1,25.05.2022"})
        void emptyStringInArgs_ExceptionThrown(String newTask) {
            assertThrows(exceptionTypeFromTaskService.getClass(),
                    () -> DataParser.parseTaskLine(exceptionTypeFromTaskService, newTask));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "1,header,description,1,25.88.2022",
                "1,header,description,1,2q.05.2022",
                "1,header,description,1,25..2022",
                "1,header,description,1,25.88.122"})
        void invalidDeadLine_ExceptionThrown(String taskLine) {
            assertThrows(exceptionTypeFromTaskService.getClass(),
                    () -> DataParser.parseTaskLine(exceptionTypeFromTaskService, taskLine));
        }

        @Test
        void invalidStatus_ExceptionThrown() {
            String taskLine = "1,header,description,1,25.05.2022,invalidStatus";
            assertThrows(exceptionTypeFromTaskService.getClass(),
                    () -> DataParser.parseTaskLine(exceptionTypeFromTaskService, taskLine));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "1,header,description,1,25.05.2022,DONE",
                "1,header,description,1,25.05.2022,done",
                "1,header,description,1,25.05.2022,DoNe"})
        void validStatus_ReturnTask(String taskLine) {
            Task task = DataParser.parseTaskLine(exceptionTypeFromTaskService, taskLine);
            Assertions.assertEquals(Task.Status.DONE, task.getStatus());
        }

        @Test
        void nonSpecifiedStatus_ReturnTaskWithNewStatus() {
            String taskLine = "1,header,description,1,25.05.2022";
            Task newTask = DataParser.parseTaskLine(exceptionTypeFromTaskService, taskLine);
            Assertions.assertEquals(Task.Status.NEW, newTask.getStatus());
        }
    }

}