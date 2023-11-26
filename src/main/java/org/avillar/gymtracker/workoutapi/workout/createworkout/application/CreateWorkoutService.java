package org.avillar.gymtracker.workoutapi.workout.createworkout.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface CreateWorkoutService {

  Workout execute(UUID userId, Workout workout)
      throws WorkoutIllegalAccessException, WorkoutForDateAlreadyExistsException;
}
