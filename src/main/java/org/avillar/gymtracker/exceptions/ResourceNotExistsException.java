package org.avillar.gymtracker.exceptions;

public class ResourceNotExistsException extends Exception {

    public ResourceNotExistsException() {
        super();
    }

    public ResourceNotExistsException(final String message) {
        super(message);
    }
}
