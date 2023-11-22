package org.example.exception;

public class CodeUniqueViolationException extends RuntimeException {

    public CodeUniqueViolationException(String message) {
        super(message);
    }
}
