package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.model.GetWorkoutSetGroupsResponseApplication;

public interface GetWorkoutSetGroupsService {

  GetWorkoutSetGroupsResponseApplication execute(UUID workoutId)
      throws EntityNotFoundException, IllegalAccessException;
}
