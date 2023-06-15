package org.avillar.gymtracker.workoutapi.workout.application.get.workout;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.get.workout.model.GetWorkoutResponse;

public interface GetWorkoutService {

  GetWorkoutResponse getWorkout(UUID workoutId, boolean full);
}
