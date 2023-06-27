package org.avillar.gymtracker.workoutapi.auth.application;

import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;

public interface AuthWorkoutsService {

  void checkAccess(Workout workout, AuthOperations authOperations);

  void checkAccess(SetGroup setGroup, AuthOperations authOperations);

  void checkAccess(Set set, AuthOperations authOperations);
}
