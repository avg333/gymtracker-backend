package org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;

public interface CheckExerciseReadAccessService {

  void execute(UUID exerciseId) throws EntityNotFoundException, IllegalAccessException;
}
