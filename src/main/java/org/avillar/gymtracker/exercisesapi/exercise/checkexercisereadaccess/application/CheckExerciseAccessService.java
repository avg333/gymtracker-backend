package org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;

public interface CheckExerciseAccessService {

  void execute(UUID exerciseId, AuthOperations authOperations)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException;
}
