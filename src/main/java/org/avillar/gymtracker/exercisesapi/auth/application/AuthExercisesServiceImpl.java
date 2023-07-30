package org.avillar.gymtracker.exercisesapi.auth.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.auth.AuthServiceBase;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.springframework.stereotype.Service;

@Service
public class AuthExercisesServiceImpl extends AuthServiceBase implements AuthExercisesService {

  @Override
  public void checkAccess(final Exercise exercise, final AuthOperations authOperations)
      throws IllegalAccessException {
    checkParameters(exercise, authOperations);

    final UUID userId = getLoggedUserId();
    if (authOperations.equals(AuthOperations.READ)) {
      checkReadAccess(exercise, userId);
    } else {
      checkModifyAccess(exercise, authOperations, userId);
    }
  }

  private void checkReadAccess(Exercise exercise, UUID userId) {
    if (exercise.getAccessType() == AccessTypeEnum.PRIVATE && !exercise.getOwner().equals(userId)) {
      throw new IllegalAccessException(exercise, AuthOperations.READ, userId);
    }
  }

  private void checkModifyAccess(Exercise exercise, AuthOperations authOperations, UUID userId) {
    if (!exercise.getOwner().equals(userId)
        || exercise.getAccessType().equals(AccessTypeEnum.PUBLIC)) {
      throw new IllegalAccessException(exercise, authOperations, userId);
    }
  }

  @Override
  public void checkAccess(final List<Exercise> exercises, final AuthOperations authOperations) {
    exercises.forEach(exercise -> this.checkAccess(exercise, authOperations));
  }

  @Override
  public UUID getLoggedUserId() {
    return super.getLoggedUserId();
  }
}
