package org.avillar.gymtracker.common.errors.application.exceptions;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

@Data
@EqualsAndHashCode(callSuper = false)
public class EntityNotFoundException extends Exception {

  private final String className;
  private final String searchParam;
  private final String param;
  private final UUID id;

  public <T> EntityNotFoundException(Class<T> entityClass, UUID id) {
    this(entityClass, "id", id);
  }

  public <T> EntityNotFoundException(Class<T> entityClass, String searchParam, UUID id) {
    super(
        EntityNotFoundException.generateMessage(
            entityClass.getSimpleName(), searchParam, id.toString()));
    this.className = entityClass.getSimpleName();
    this.searchParam = searchParam;
    this.id = id;
    this.param = id.toString();
  }

  public <T> EntityNotFoundException(Class<T> entityClass, String searchParam, String param) {
    super(EntityNotFoundException.generateMessage(entityClass.getSimpleName(), searchParam, param));
    this.className = entityClass.getSimpleName();
    this.searchParam = searchParam;
    this.param = param;
    this.id = null;
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
