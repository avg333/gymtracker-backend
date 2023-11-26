package org.avillar.gymtracker.workoutapi.workout.deleteworkout.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;

public interface DeleteWorkoutService {

  void execute(UUID workoutId) throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
