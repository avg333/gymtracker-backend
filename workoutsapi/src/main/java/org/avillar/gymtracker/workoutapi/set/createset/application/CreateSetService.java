package org.avillar.gymtracker.workoutapi.set.createset.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;

public interface CreateSetService {
  CreateSetResponseApplication execute(
      UUID setGroupId, CreateSetRequestApplication createSetRequestApplication)
      throws EntityNotFoundException, IllegalAccessException;
}
