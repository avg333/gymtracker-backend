package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface UpdateSetGroupDescriptionService {

  String execute(UUID setGroupId, String description)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
