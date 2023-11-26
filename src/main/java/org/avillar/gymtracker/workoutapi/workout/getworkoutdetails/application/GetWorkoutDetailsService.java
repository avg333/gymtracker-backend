package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;

public interface GetWorkoutDetailsService {

  Workout execute(UUID workoutId) throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
