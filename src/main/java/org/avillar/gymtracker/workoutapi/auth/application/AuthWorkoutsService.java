package org.avillar.gymtracker.workoutapi.auth.application;

import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;

public interface AuthWorkoutsService {

  void checkAccess(Workout workout, AuthOperations authOperations);

  void checkAccess(SetGroup setGroup, AuthOperations authOperations);

  void checkAccess(Set set, AuthOperations authOperations);
}
