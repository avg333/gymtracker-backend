package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface DeleteSetGroupService {

  void execute(UUID setGroupId) throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
