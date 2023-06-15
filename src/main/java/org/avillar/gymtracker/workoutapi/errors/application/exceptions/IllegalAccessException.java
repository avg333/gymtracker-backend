package org.avillar.gymtracker.workoutapi.errors.application.exceptions;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;

@Data
@EqualsAndHashCode(callSuper = false)
public class IllegalAccessException extends RuntimeException {

  private final String entityClassName;
  private final UUID entityId;
  private final AuthOperations authOperations;
  private final UUID currentUserId;

  public <T extends BaseEntity, I extends BaseEntity> IllegalAccessException(
      T entity, AuthOperations authOperations, UUID userId) {
    super(
        IllegalAccessException.generateMessage(
            entity.getClass().getSimpleName(),
            entity.getId().toString(),
            authOperations.toString(),
            userId.toString()));
    this.entityClassName = entity.getClass().getSimpleName();
    this.entityId = entity.getId();
    this.authOperations = authOperations;
    this.currentUserId = userId;
  }

  private static String generateMessage(
      String className, String entityId, String operation, String currentUserId) {
    return "Ilegal "
        + operation
        + " to entity "
        + className
        + "with id = "
        + entityId
        + " by user with id = "
        + currentUserId;
  }
}
