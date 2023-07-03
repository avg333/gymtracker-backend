package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application;

import java.util.Date;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;

public interface UpdateWorkoutDateService {

  Date execute(UUID workoutId, Date date)
      throws EntityNotFoundException, DuplicatedWorkoutDateException, IllegalAccessException;
}
