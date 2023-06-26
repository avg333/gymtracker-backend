package org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.model.GetWorkoutSetGroupsResponseApplication;

public interface GetWorkoutSetGroupsService {

  GetWorkoutSetGroupsResponseApplication execute(UUID workoutId);
}
