package org.avillar.gymtracker.errors.application;

import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.user.domain.UserApp;
import org.springframework.util.StringUtils;

public class IllegalAccessException extends ReflectiveOperationException {

    public <T extends BaseEntity> IllegalAccessException(T entity, String operation, UserApp userApp, String id) {
        super(IllegalAccessException.generateMessage(entity.getClass().getSimpleName(), operation, userApp.getId().toString(), id));
    }

    private static String generateMessage(String className, String operation, String userLogged, String id) {
        return StringUtils.capitalize(className) +
                " ilegal " + operation + " with id = " + id + " by user with id = " + userLogged;
    }
}
