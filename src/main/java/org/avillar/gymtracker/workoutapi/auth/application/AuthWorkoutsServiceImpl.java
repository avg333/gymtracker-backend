package org.avillar.gymtracker.workoutapi.auth.application;

import java.util.UUID;
import org.avillar.gymtracker.common.auth.AuthServiceBase;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.springframework.stereotype.Service;

@Service
public class AuthWorkoutsServiceImpl extends AuthServiceBase implements AuthWorkoutsService {
  // TODO Se deberia devolver excepcion de la clase que no se tiene acceso y su id, no el WO
  @Override
  public void checkAccess(final Workout workout, final AuthOperations authOperations)
      throws IllegalAccessException {
    checkParameters(workout, authOperations);

    final UUID userId = getLoggedUserId();
    if (!userId.equals(workout.getUserId())) {
      throw new IllegalAccessException(workout, authOperations, userId);
    }
  }

  @Override
  public void checkAccess(final SetGroup setGroup, final AuthOperations authOperations)
      throws IllegalAccessException {
    checkParameters(setGroup, authOperations);
    checkAccess(setGroup.getWorkout(), authOperations);
  }

  @Override
  public void checkAccess(final Set set, final AuthOperations authOperations)
      throws IllegalAccessException {
    checkParameters(set, authOperations);
    checkAccess(set.getSetGroup(), authOperations);
  }
}
