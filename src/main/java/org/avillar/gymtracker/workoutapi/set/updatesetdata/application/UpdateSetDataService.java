package org.avillar.gymtracker.workoutapi.set.updatesetdata.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;

public interface UpdateSetDataService {

  UpdateSetDataResponseApplication execute(
      UUID setId, UpdateSetDataRequestApplication updateSetDataRequestApplication)
      throws EntityNotFoundException, IllegalAccessException;
}
