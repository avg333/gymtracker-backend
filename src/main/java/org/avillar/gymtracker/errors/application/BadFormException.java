package org.avillar.gymtracker.errors.application;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.BindingResult;

@Data
@EqualsAndHashCode(callSuper = false)
public class BadFormException extends RuntimeException {

    private final String className;
    private final BindingResult bindingResult;

    public <T> BadFormException(Class<T> entityClass, BindingResult bindingResult) {
        super("Clase " + entityClass.getSimpleName() + " mal formada");
        this.className = entityClass.getSimpleName();
        this.bindingResult = bindingResult;
    }
}
