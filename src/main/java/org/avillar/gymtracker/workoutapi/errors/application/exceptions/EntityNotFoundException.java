package org.avillar.gymtracker.workoutapi.errors.application.exceptions;

import jakarta.persistence.PersistenceException;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.springframework.util.StringUtils;

@Data
@EqualsAndHashCode(callSuper = false)
public class EntityNotFoundException extends PersistenceException {

  private final String className;
  private final UUID id;

  public <T extends BaseEntity> EntityNotFoundException(Class<T> entityClass, UUID id) {
    super(
        EntityNotFoundException.generateMessage(entityClass.getSimpleName(), "id", id.toString()));
    className = entityClass.getSimpleName();
    this.id = id;
  }

  private static String generateMessage(
      String className, String searchParamType, String searchParamsValue) {
    return StringUtils.capitalize(className)
        + " was not found for parameters "
        + searchParamType
        + " = "
        + searchParamsValue;
  }
}
