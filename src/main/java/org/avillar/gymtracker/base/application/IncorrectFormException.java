package org.avillar.gymtracker.base.application;

public class IncorrectFormException extends Exception{
    public IncorrectFormException(final String errorMessage){
        super(errorMessage);
    }
}
