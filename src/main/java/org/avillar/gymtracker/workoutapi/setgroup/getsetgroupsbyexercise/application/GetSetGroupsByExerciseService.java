package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetSetGroupsByExerciseResponseApplication;

public interface GetSetGroupsByExerciseService {

  List<GetSetGroupsByExerciseResponseApplication> execute(UUID userId, UUID exerciseId)
      throws IllegalAccessException;
}
