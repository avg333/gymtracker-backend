package org.avillar.gymtracker.workoutapi.set.deleteset.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;

public interface DeleteSetService {

  void execute(UUID setId) throws EntityNotFoundException, IllegalAccessException;
}
