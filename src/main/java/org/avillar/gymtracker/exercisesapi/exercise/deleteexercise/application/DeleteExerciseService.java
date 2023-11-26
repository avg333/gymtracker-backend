package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;

public interface DeleteExerciseService {

  void execute(UUID exerciseId) throws ExerciseNotFoundException, ExerciseIllegalAccessException;
}
