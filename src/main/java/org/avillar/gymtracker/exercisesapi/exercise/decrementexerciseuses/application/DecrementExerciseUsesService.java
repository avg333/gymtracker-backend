package org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.model.DecrementExerciseUsesResponseApplication;

public interface DecrementExerciseUsesService {

  DecrementExerciseUsesResponseApplication execute(UUID exerciseId);
}
