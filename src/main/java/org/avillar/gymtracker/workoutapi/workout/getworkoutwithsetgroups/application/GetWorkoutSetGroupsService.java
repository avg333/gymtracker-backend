package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.model.GetWorkoutSetGroupsResponseApplication;

public interface GetWorkoutSetGroupsService {

  GetWorkoutSetGroupsResponseApplication execute(UUID workoutId);
}
