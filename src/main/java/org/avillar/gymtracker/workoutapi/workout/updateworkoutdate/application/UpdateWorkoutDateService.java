package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application;

import java.time.LocalDate;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;

public interface UpdateWorkoutDateService {

  LocalDate execute(UUID workoutId, LocalDate date)
      throws WorkoutNotFoundException,
          WorkoutForDateAlreadyExistsException,
          WorkoutIllegalAccessException;
}
