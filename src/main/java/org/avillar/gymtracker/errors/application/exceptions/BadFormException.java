package org.avillar.gymtracker.errors.application.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class BadFormException extends RuntimeException {

    private final String className;
    private final List<ObjectError> objectErrors;

    public <T> BadFormException(Class<T> entityClass, BindingResult bindingResult) {
        super("Clase " + entityClass.getSimpleName() + " mal formada");
        this.className = entityClass.getSimpleName();
        this.objectErrors = bindingResult.getAllErrors();
    }
}
