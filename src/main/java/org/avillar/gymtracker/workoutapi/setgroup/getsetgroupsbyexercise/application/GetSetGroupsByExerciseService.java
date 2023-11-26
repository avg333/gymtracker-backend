package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface GetSetGroupsByExerciseService {

  List<SetGroup> execute(UUID userId, UUID exerciseId) throws WorkoutIllegalAccessException;
}
