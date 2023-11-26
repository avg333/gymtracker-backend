package org.avillar.gymtracker.workoutapi.common.auth.application;

import java.util.UUID;
import org.avillar.gymtracker.common.auth.AuthServiceBase;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.springframework.stereotype.Service;

@Service
public class AuthWorkoutsServiceImpl extends AuthServiceBase implements AuthWorkoutsService {
  @Override
  public void checkAccess(final Workout workout, final AuthOperations authOperations)
      throws WorkoutIllegalAccessException {
    checkParameters(workout, authOperations); // TODO Test or improve this

    final UUID userId = getLoggedUserId();
    if (!userId.equals(workout.getUserId())) {
      throw new WorkoutIllegalAccessException(workout.getId(), authOperations, userId);
    }
  }

  @Override
  public void checkAccess(final SetGroup setGroup, final AuthOperations authOperations)
      throws WorkoutIllegalAccessException {
    checkAccess(setGroup.getWorkout(), authOperations);
  }

  @Override
  public void checkAccess(final Set set, final AuthOperations authOperations)
      throws WorkoutIllegalAccessException {
    checkAccess(set.getSetGroup(), authOperations);
  }
}
