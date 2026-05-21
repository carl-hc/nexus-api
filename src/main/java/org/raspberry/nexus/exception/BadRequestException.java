package org.raspberry.nexus.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message, Object... args) {
        super(String.format(message, args));
    }

    public BadRequestException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

}
