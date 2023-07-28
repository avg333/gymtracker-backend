package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application;

import java.util.Date;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.exception.application.DuplicatedWorkoutDateException;

public interface UpdateWorkoutDateService {

  Date execute(UUID workoutId, Date date)
      throws EntityNotFoundException, DuplicatedWorkoutDateException, IllegalAccessException;
}
