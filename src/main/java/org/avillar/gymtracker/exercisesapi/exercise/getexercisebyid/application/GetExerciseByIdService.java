package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;

public interface GetExerciseByIdService {

  GetExerciseByIdResponseApplication execute(UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException;
}
