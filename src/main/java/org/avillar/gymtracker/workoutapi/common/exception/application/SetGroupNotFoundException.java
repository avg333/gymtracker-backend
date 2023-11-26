package org.avillar.gymtracker.workoutapi.common.exception.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;

public class SetGroupNotFoundException extends EntityNotFoundException {

  public SetGroupNotFoundException(final UUID id) {
    super(SetGroup.class, id);
  }
}
