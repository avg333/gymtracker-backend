package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;

public interface CreateSetGroupService {

  SetGroup execute(UUID workoutId, SetGroup setGroup)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException;
}
