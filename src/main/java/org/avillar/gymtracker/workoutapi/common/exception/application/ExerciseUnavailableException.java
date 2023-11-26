package org.avillar.gymtracker.workoutapi.common.exception.application;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ExerciseUnavailableException extends Exception {

  private final UUID exerciseId;
  private final AccessError accessError;

  public ExerciseUnavailableException(final UUID exerciseId, final AccessError accessError) {
    super("Exercise with ID: " + exerciseId + " is not usable due to access error: " + accessError);
    this.exerciseId = exerciseId;
    this.accessError = accessError;
  }

  public enum AccessError {
    NOT_FOUND,
    NOT_ACCESS,
    UNKNOWN
  }
}
