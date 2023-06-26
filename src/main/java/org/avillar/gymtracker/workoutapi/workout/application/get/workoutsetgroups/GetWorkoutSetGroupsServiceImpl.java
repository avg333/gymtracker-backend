package org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.mapper.GetWorkoutSetGroupsServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.model.GetWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkoutSetGroupsServiceImpl implements GetWorkoutSetGroupsService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetWorkoutSetGroupsServiceMapper getWorkoutSetGroupsServiceMapper;

  @Override
  public GetWorkoutSetGroupsResponseApplication execute(final UUID workoutId) {
    final Workout workout = getWorkoutWithSetGroups(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    return getWorkoutSetGroupsServiceMapper.map(workout);
  }

  private Workout getWorkoutWithSetGroups(final UUID workoutId) {
    return workoutDao.getWorkoutWithSetGroupsById(workoutId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }
}
