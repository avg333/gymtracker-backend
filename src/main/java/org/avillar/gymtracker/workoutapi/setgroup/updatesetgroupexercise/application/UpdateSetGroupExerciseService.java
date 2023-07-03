package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;

public interface UpdateSetGroupExerciseService {

  UUID execute(UUID setGroupId, UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException;
}
