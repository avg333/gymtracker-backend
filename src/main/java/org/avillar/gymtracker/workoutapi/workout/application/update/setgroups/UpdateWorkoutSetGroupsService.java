package org.avillar.gymtracker.workoutapi.workout.application.update.setgroups;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.model.UpdateWorkoutSetGroupsResponseApplication;

public interface UpdateWorkoutSetGroupsService {

  UpdateWorkoutSetGroupsResponseApplication addSetGroupsToWorkoutFromWorkout(
      UUID workoutDestinationId, UUID workoutSourceId);

  UpdateWorkoutSetGroupsResponseApplication addSetGroupsToWorkoutFromSession(
      UUID workoutDestinationId, UUID sessionSourceId);
}
