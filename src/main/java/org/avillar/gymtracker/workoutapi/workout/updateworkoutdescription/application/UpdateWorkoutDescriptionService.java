package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;

public interface UpdateWorkoutDescriptionService {

  String execute(UUID workoutId, String description)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
