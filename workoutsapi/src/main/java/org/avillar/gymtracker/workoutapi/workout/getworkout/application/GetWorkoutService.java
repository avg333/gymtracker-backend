package org.avillar.gymtracker.workoutapi.workout.getworkout.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutResponseApplication;

public interface GetWorkoutService {

  GetWorkoutResponseApplication execute(UUID workoutId)
      throws EntityNotFoundException, IllegalAccessException;
}
