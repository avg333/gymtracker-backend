package org.avillar.gymtracker.common.errors.application.exceptions;

import java.util.Objects;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.avillar.gymtracker.common.errors.application.AuthOperations;

@Data
@EqualsAndHashCode(callSuper = false)
public class IllegalAccessException extends Exception {

  private final String entityClassName;
  private final UUID entityId;
  private final AuthOperations authOperations;
  private final UUID currentUserId;

  public IllegalAccessException(
      Class<?> className, UUID entityId, AuthOperations authOperations, UUID userId) {
    super(
        IllegalAccessException.generateMessage(
            className.getSimpleName(),
            Objects.nonNull(entityId) ? entityId.toString() : "",
            authOperations.toString(),
            userId.toString()));
    this.entityClassName = className.getSimpleName();
    this.entityId = entityId;
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
