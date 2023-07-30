package org.avillar.gymtracker.exercisesapi.workout.application.facade;

import java.util.UUID;

public interface WorkoutRepositoryClient {

  int getExerciseUsesNumberForUser(UUID exerciseId, UUID userId);
}
