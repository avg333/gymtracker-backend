package org.avillar.gymtracker.workoutapi.setgroup.application.update.exercise;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.springframework.stereotype.Service;

// RDY
@Service
@RequiredArgsConstructor
public class UpdateSetGroupExerciseServiceImpl implements UpdateSetGroupExerciseService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final ExerciseRepositoryClient exerciseRepositoryClient;

  @Override
  public UUID update(final UUID setGroupId, final UUID exerciseId) {
    final SetGroup setGroup = getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);

    if (setGroup.getExerciseId().equals(exerciseId)) {
      return setGroup.getExerciseId();
    }

    if (!exerciseRepositoryClient.canAccessExerciseById(exerciseId)) {
      throw new EntityNotFoundException(Exercise.class, exerciseId);
    }

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
