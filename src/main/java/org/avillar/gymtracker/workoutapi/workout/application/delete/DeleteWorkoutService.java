package org.avillar.gymtracker.workoutapi.workout.application.delete;

import java.util.UUID;

public interface DeleteWorkoutService {

  void delete(UUID workoutId);
}