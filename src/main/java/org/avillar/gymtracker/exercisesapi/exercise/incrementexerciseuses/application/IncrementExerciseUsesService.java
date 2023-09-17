package org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.model.IncrementExerciseUsesResponseApplication;

public interface IncrementExerciseUsesService {

  IncrementExerciseUsesResponseApplication execute(UUID exerciseId);
}
