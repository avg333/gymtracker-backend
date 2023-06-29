package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.mapper.CreateSetGroupServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSetGroupServiceImpl implements CreateSetGroupService {

  private final SetGroupDao setGroupDao;
  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final CreateSetGroupServiceMapper createSetGroupServiceMapper;
  private final ExerciseRepositoryClient exerciseRepositoryClient;

  @Override
  public CreateSetGroupResponseApplication execute(
      final UUID workoutId, final CreateSetGroupRequestApplication createSetGroupRequestApplication)
      throws EntityNotFoundException, IllegalAccessException {
    final Workout workout = getWorkoutWithSetGroups(workoutId);

    final SetGroup setGroup = createSetGroupServiceMapper.map(createSetGroupRequestApplication);
    setGroup.setWorkout(workout);
    setGroup.setListOrder(workout.getSetGroups().size());

    if (!exerciseRepositoryClient.canAccessExerciseById(setGroup.getExerciseId())) {
      throw new EntityNotFoundException(Exercise.class, setGroup.getExerciseId());
    } // TODO Cambiar excepcion

    authWorkoutsService.checkAccess(setGroup, AuthOperations.CREATE);

    setGroupDao.save(setGroup);

    return createSetGroupServiceMapper.map(setGroup);
  }

  private Workout getWorkoutWithSetGroups(final UUID workoutId) {
    return workoutDao.getWorkoutWithSetGroupsById(workoutId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }
}
