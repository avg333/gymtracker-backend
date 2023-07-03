package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;

public interface UpdateSetGroupDescriptionService {

  String execute(UUID setGroupId, String description)
      throws EntityNotFoundException, IllegalAccessException;
}
