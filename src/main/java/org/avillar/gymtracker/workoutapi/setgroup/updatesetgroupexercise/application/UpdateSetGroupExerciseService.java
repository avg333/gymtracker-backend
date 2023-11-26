package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface UpdateSetGroupExerciseService {

  UUID execute(UUID setGroupId, UUID exerciseId)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException;
}
