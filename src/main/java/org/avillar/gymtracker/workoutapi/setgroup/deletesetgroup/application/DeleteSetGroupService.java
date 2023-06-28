package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;

public interface DeleteSetGroupService {

  void execute(UUID setGroupId) throws EntityNotFoundException, IllegalAccessException;
}
