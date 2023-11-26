package org.avillar.gymtracker.workoutapi.workout.getworkout.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;

public interface GetWorkoutService {

  Workout execute(UUID workoutId) throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
