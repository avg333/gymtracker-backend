package org.avillar.gymtracker.exercisesapi.common.auth.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.auth.AuthServiceBase;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.springframework.stereotype.Service;

@Service
public class AuthExercisesServiceImpl extends AuthServiceBase implements AuthExercisesService {

  @Override
  public void checkAccess(final Exercise exercise, final AuthOperations authOperations)
      throws ExerciseIllegalAccessException {
    checkParameters(exercise, authOperations);

    final UUID userId = getLoggedUserId();
    if (authOperations.equals(AuthOperations.READ)) {
      checkReadAccess(exercise, userId);
    } else {
      checkModifyAccess(exercise, authOperations, userId);
    }
  }

  private void checkReadAccess(Exercise exercise, UUID userId)
      throws ExerciseIllegalAccessException {
    if (exercise.getAccessType() == AccessTypeEnum.PRIVATE && !exercise.getOwner().equals(userId)) {
      throw new ExerciseIllegalAccessException(exercise.getId(), AuthOperations.READ, userId);
    }
  }

  private void checkModifyAccess(Exercise exercise, AuthOperations authOperations, UUID userId)
      throws ExerciseIllegalAccessException {
    if (!exercise.getOwner().equals(userId)
        || exercise.getAccessType().equals(AccessTypeEnum.PUBLIC)) {
      throw new ExerciseIllegalAccessException(exercise.getId(), authOperations, userId);
    }
  }

  @Override
  public void checkAccess(final List<Exercise> exercises, final AuthOperations authOperations)
      throws ExerciseIllegalAccessException {
    for (Exercise exercise : exercises) {
      this.checkAccess(exercise, authOperations);
    }
  }

  @Override
  public UUID getLoggedUserId() {
    return super.getLoggedUserId();
  }
}
