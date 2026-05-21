package org.raspberry.nexus.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }

    public NotFoundException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

}
