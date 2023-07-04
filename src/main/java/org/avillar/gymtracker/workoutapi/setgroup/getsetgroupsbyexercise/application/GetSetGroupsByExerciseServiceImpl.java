package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.mapper.GetSetGroupsByExerciseServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetSetGroupsByExerciseResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSetGroupsByExerciseServiceImpl implements GetSetGroupsByExerciseService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetSetGroupsByExerciseServiceMapper getSetGroupsByExerciseServiceMapper;

  @Override
  public List<GetSetGroupsByExerciseResponseApplication> execute(
      final UUID userId, final UUID exerciseId) throws IllegalAccessException {

    checkAccess(userId);

    return getSetGroupsByExerciseServiceMapper.map(
        setGroupDao.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId));
  }

  private void checkAccess(final UUID userId) {
    final Workout workout = new Workout();
    workout.setUserId(userId);
    final SetGroup setGroup = new SetGroup();
    setGroup.setWorkout(workout);
    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);
  }
}
