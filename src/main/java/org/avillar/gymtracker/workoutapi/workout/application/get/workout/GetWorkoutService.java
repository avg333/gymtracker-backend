package org.avillar.gymtracker.workoutapi.workout.application.get.workout;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.get.workout.model.GetWorkoutResponseApplication;

public interface GetWorkoutService {

  GetWorkoutResponseApplication execute(UUID workoutId);
}
