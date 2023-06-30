package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.CopySetGroupsResponseApplication;

public interface CopySetGroupsService {

  List<CopySetGroupsResponseApplication> execute(
      UUID workoutDestinationId, UUID workoutSourceId, boolean sourceWorkout)
      throws EntityNotFoundException, IllegalAccessException;
}
