package ru.internship.perepichka.exception;

public class BadIdException extends RuntimeException{
    public BadIdException(String message) {
        super(message);
    }
}
