package org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.mapper.GetExerciseSetGroupsServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.model.GetExerciseSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.springframework.stereotype.Service;

// FINALIZAR
@Service
@RequiredArgsConstructor
public class GetExerciseSetGroupsServiceImpl implements GetExerciseSetGroupsService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetExerciseSetGroupsServiceMapper getExerciseSetGroupsServiceMapper;

  @Override
  public GetExerciseSetGroupsResponseApplication execute(final UUID userId, final UUID exerciseId) {
    final Workout workout = new Workout();
    workout.setUserId(userId);
    final SetGroup setGroup = new SetGroup();
    setGroup.setWorkout(workout);
    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);

    return new GetExerciseSetGroupsResponseApplication(
        getExerciseSetGroupsServiceMapper.map(
            setGroupDao.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId)));
  }
}
