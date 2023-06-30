package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.model.GetNewSetDataResponseApplication;

public interface GetNewSetDataService {

  GetNewSetDataResponseApplication execute(UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException;
}
