package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutResponseApplication;

public interface GetWorkoutService {

  GetWorkoutResponseApplication execute(UUID workoutId);
}
