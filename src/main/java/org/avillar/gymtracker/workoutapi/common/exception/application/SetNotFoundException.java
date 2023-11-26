package org.avillar.gymtracker.workoutapi.common.exception.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.common.domain.Set;

public class SetNotFoundException extends EntityNotFoundException {

  public SetNotFoundException(final UUID id) {
    super(Set.class, id);
  }
}
