package org.avillar.gymtracker.exercisesapi.auth.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;

public interface AuthExercisesService {

  void checkAccess(Exercise exercise, AuthOperations authOperations);

  void checkAccess(List<Exercise> exercises, AuthOperations authOperations);

  UUID getLoggedUserId();
}
