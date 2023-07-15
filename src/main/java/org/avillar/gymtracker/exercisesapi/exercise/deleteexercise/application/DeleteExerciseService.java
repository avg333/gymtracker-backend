package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;

public interface DeleteExerciseService {

  void execute(UUID exerciseId) throws EntityNotFoundException, IllegalAccessException;
}
