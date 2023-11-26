package org.avillar.gymtracker.exercisesapi.common.auth.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;

public interface AuthExercisesService {

  void checkAccess(Exercise exercise, AuthOperations authOperations)
      throws ExerciseIllegalAccessException;

  void checkAccess(List<Exercise> exercises, AuthOperations authOperations)
      throws ExerciseIllegalAccessException;

  UUID getLoggedUserId();
}
