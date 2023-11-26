package org.avillar.gymtracker.exercisesapi.common.facade.workout;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface WorkoutsFacade {

  int getExerciseUsesNumberForUser(UUID exerciseId, UUID userId)
      throws WorkoutIllegalAccessException;
}
