package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;

public interface GetExerciseByIdService {

  Exercise execute(UUID exerciseId)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException;
}
