package ru.exist.ecom.core.application.exception;

public class DraftNotFoundException extends RuntimeException {
    public DraftNotFoundException(String message) {
        super(message);
    }
}