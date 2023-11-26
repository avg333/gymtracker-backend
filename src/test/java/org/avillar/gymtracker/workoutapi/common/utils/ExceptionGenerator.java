package org.avillar.gymtracker.workoutapi.common.utils;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException.AccessError;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;

public class ExceptionGenerator {

  public static WorkoutNotFoundException generateWorkoutNotFoundException() {
    return new WorkoutNotFoundException(UUID.randomUUID());
  }

  public static SetGroupNotFoundException generateSetGroupNotFoundException() {
    return new SetGroupNotFoundException(UUID.randomUUID());
  }

  public static SetNotFoundException generateSetNotFoundException() {
    return new SetNotFoundException(UUID.randomUUID());
  }

  public static WorkoutIllegalAccessException generateWorkoutIllegalAccessException() {
    return new WorkoutIllegalAccessException(
        UUID.randomUUID(), AuthOperations.READ, UUID.randomUUID());
  }

  public static ExerciseUnavailableException generateExerciseUnavailableException() {
    return new ExerciseUnavailableException(UUID.randomUUID(), AccessError.NOT_FOUND);
  }
}
