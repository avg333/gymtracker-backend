package org.avillar.gymtracker.workoutapi.workout.getworkout.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutSimpleResponseApplication;

public interface GetWorkoutSimpleService {

  GetWorkoutSimpleResponseApplication execute(UUID workoutId);
}
