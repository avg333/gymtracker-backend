package org.avillar.gymtracker.common.errors.application.exceptions;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExerciseNotFoundException extends RuntimeException { // TODO Extend EntityNotFound?

  private static final String className = "Exercise";
  private static final String searchParam = "id";
  private final UUID id;
  private final AccessError accessError;

  public ExerciseNotFoundException(UUID id, AccessError accessError) {
    super();
    this.id = id;
    this.accessError = accessError;
  }

  public enum AccessError {
    NOT_FOUND,
    NOT_ACCESS,
    UNKNOWN
  }
}
