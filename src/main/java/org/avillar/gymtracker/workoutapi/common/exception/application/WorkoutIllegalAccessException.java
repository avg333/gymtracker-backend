package org.avillar.gymtracker.workoutapi.common.exception.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;

public class WorkoutIllegalAccessException extends IllegalAccessException {

  public WorkoutIllegalAccessException(
      final UUID workoutId, final AuthOperations authOperations, final UUID userId) {
    super(Workout.class, workoutId, authOperations, userId);
  }
}
