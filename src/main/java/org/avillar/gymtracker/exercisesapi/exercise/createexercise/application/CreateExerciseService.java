package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseResponseApplication;

public interface CreateExerciseService {

  CreateExerciseResponseApplication execute(
      UUID userId, CreateExerciseRequestApplication createExerciseRequestApplication)
      throws EntityNotFoundException, IllegalAccessException;
}
