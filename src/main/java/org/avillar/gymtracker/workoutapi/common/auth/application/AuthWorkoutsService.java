package org.avillar.gymtracker.workoutapi.common.auth.application;

import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;

public interface AuthWorkoutsService {

  void checkAccess(Workout workout, AuthOperations authOperations)
      throws WorkoutIllegalAccessException;

  void checkAccess(SetGroup setGroup, AuthOperations authOperations)
      throws WorkoutIllegalAccessException;

  void checkAccess(Set set, AuthOperations authOperations) throws WorkoutIllegalAccessException;
}
