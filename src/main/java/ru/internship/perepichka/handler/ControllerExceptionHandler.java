package ru.internship.perepichka.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.internship.perepichka.exception.BadCommandException;
import ru.internship.perepichka.exception.BadIdException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CommandErrorResponse> handleException(BadCommandException exc) {
        CommandErrorResponse error = new CommandErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CommandErrorResponse> handleException(BadIdException exc) {
        CommandErrorResponse error = new CommandErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}
