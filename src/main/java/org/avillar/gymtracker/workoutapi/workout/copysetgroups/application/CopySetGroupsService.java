package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsServiceImpl.CopySetGroupsRequest;

public interface CopySetGroupsService {

  List<SetGroup> execute(UUID workoutDestinationId, CopySetGroupsRequest copySetGroupsRequest)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
