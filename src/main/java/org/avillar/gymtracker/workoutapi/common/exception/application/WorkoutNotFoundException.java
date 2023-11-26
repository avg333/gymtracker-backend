package org.avillar.gymtracker.workoutapi.common.exception.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;

public class WorkoutNotFoundException extends EntityNotFoundException {

  public WorkoutNotFoundException(final UUID id) {
    super(Workout.class, id);
  }
}
