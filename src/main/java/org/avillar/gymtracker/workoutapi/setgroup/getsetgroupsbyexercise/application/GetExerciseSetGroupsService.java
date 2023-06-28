package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetExerciseSetGroupsResponseApplication;

public interface GetExerciseSetGroupsService {

  GetExerciseSetGroupsResponseApplication execute(UUID userId, UUID exerciseId);
}
