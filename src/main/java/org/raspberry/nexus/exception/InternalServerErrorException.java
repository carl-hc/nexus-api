package org.raspberry.nexus.exception;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String message, Object... args) {
        super(String.format(message, args));
    }

    public InternalServerErrorException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

}
