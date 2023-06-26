package org.avillar.gymtracker.workoutapi.workout.application.get.workoutsimple;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsimple.model.GetWorkoutSimpleResponseApplication;

public interface GetWorkoutSimpleService {

  GetWorkoutSimpleResponseApplication execute(UUID workoutId);
}
