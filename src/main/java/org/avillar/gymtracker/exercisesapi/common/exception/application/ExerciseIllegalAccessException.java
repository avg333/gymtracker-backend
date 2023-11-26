package org.avillar.gymtracker.exercisesapi.common.exception.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;

public class ExerciseIllegalAccessException extends IllegalAccessException {

  public ExerciseIllegalAccessException(
      UUID exerciseId, AuthOperations authOperations, UUID userId) {
    super(Exercise.class, exerciseId, authOperations, userId);
  }
}
