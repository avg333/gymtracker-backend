package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetGroupExerciseServiceImpl implements UpdateSetGroupExerciseService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final ExerciseRepositoryClient exerciseRepositoryClient;

  @Override
  public UUID execute(final UUID setGroupId, final UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException {
    final SetGroup setGroup = getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);

    if (setGroup.getExerciseId().equals(exerciseId)) {
      return setGroup.getExerciseId();
    }

    if (!exerciseRepositoryClient.canAccessExerciseById(exerciseId)) {
      throw new EntityNotFoundException(Exercise.class, exerciseId);
    } // TODO Cambiar excepcion

    setGroup.setExerciseId(exerciseId);

    setGroupDao.save(setGroup);

    return setGroup.getExerciseId();
  }

  private SetGroup getSetGroupWithWorkout(final UUID setGroupId) {
    return setGroupDao.getSetGroupWithWorkoutById(setGroupId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
