package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;

public interface GetSetGroupService {

  GetSetGroupResponseApplication execute(UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException;
}
