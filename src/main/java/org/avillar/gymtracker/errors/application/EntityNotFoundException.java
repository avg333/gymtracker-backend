package org.avillar.gymtracker.errors.application;

import org.springframework.util.StringUtils;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String className, String searchParamType, String searchParamsValue) {
        super(EntityNotFoundException.generateMessage(className, searchParamType, searchParamsValue));
    }

    private static String generateMessage(String entity, String searchParamType, String searchParamsValue) {
        return StringUtils.capitalize(entity) +
                " was not found for parameters " +
                searchParamType + " = " + searchParamsValue;
    }
}
