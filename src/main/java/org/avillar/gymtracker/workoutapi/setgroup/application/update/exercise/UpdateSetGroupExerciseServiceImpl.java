package org.avillar.gymtracker.workoutapi.setgroup.application.update.exercise;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetGroupExerciseServiceImpl implements UpdateSetGroupExerciseService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public UUID update(final UUID setGroupId, final UUID exerciseId) {
    final SetGroup setGroup = getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);

    if (setGroup.getExerciseId().equals(exerciseId)) {
      return setGroup.getExerciseId();
    }

    // TODO Verificar si el ejercicio existe y es accesible para el usuario

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
