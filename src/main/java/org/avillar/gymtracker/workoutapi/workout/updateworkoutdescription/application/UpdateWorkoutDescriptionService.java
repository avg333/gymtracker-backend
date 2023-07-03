package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;

public interface UpdateWorkoutDescriptionService {

  String execute(UUID workoutId, String date)
      throws EntityNotFoundException, IllegalAccessException;
}
