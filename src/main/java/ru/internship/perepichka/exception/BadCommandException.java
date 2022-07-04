package ru.internship.perepichka.exception;

public class BadCommandException extends RuntimeException {
    public BadCommandException(String message) {
        super(message);
    }
}
