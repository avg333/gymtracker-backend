package org.avillar.gymtracker.exercisesapi.common.exception.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;

public class ExerciseNotFoundException extends EntityNotFoundException {

  public ExerciseNotFoundException(final UUID id) {
    super(Exercise.class, id);
  }
}
