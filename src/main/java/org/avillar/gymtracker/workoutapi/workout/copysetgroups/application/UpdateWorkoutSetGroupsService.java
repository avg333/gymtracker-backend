package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.UpdateWorkoutSetGroupsResponseApplication;

public interface UpdateWorkoutSetGroupsService {

  UpdateWorkoutSetGroupsResponseApplication addSetGroupsToWorkoutFromWorkout(
      UUID workoutDestinationId, UUID workoutSourceId);

  UpdateWorkoutSetGroupsResponseApplication addSetGroupsToWorkoutFromSession(
      UUID workoutDestinationId, UUID sessionSourceId);
}
