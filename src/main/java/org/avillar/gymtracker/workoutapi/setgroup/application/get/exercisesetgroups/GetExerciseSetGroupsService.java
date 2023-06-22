package org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.model.GetExerciseSetGroupsResponseApplication;

public interface GetExerciseSetGroupsService {

  GetExerciseSetGroupsResponseApplication execute(UUID userId, UUID exerciseId);
}
