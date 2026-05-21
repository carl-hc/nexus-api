package org.raspberry.nexus.exception;

public class ConflictException extends RuntimeException {

    public ConflictException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ConflictException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

}
