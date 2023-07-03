package org.avillar.gymtracker.workoutapi.workout.deleteworkout.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;

public interface DeleteWorkoutService {

  void execute(UUID workoutId) throws EntityNotFoundException, IllegalArgumentException;
}
