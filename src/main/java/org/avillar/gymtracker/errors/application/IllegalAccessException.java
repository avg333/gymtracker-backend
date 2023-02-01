package org.avillar.gymtracker.errors.application;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.user.domain.UserApp;

@Data
@EqualsAndHashCode(callSuper = false)
public class IllegalAccessException extends ReflectiveOperationException {

    private final String entityClassName;
    private final Long entityId;
    private final String operation;
    private final Long currentUserId;

    public <T extends BaseEntity, I extends BaseEntity> IllegalAccessException(T entity, String operation, UserApp userApp) {
        super(IllegalAccessException.generateMessage(entity.getClass().getSimpleName(), entity.getId().toString(), operation, userApp.getId().toString()));
        this.entityClassName = entity.getClass().getSimpleName();
        this.entityId = entity.getId();
        this.operation = operation;
        this.currentUserId = userApp.getId();

    }

    private static String generateMessage(String className, String entityId, String operation, String currentUserId) {
        return "Ilegal " + operation + " to entity " + className + "with id = " + entityId + " by user with id = " + currentUserId;
    }
}
