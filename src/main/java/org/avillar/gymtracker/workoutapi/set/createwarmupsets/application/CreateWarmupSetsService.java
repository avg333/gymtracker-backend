package org.avillar.gymtracker.workoutapi.set.createwarmupsets.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.application.CreateWarmupSetsServiceImpl.Exhaustiveness;

public interface CreateWarmupSetsService {

  List<Set> execute(UUID setGroupId, Set set, Exhaustiveness exhaustiveness)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException;
}
