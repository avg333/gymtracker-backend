package org.avillar.gymtracker.workoutapi.set.createset.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface CreateSetService {
  Set execute(UUID setGroupId, Set set)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
