package org.avillar.gymtracker.workoutapi.workout.application.update.setgroups;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.model.UpdateWorkoutSetGroupsResponse;

public interface UpdateWorkoutSetGroupsService {

  UpdateWorkoutSetGroupsResponse addSetGroupsToWorkoutFromWorkout(
      UUID workoutDestinationId, UUID workoutSourceId);

  UpdateWorkoutSetGroupsResponse addSetGroupsToWorkoutFromSession(
      UUID workoutDestinationId, UUID sessionSourceId);
}
