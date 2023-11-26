package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSetGroupsByExerciseServiceImpl implements GetSetGroupsByExerciseService {

  private final SetGroupFacade setGroupFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public List<SetGroup> execute(final UUID userId, final UUID exerciseId)
      throws WorkoutIllegalAccessException {

    checkAccess(userId);

    return setGroupFacade.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId);
  }

  private void checkAccess(final UUID userId) throws WorkoutIllegalAccessException {
    final Workout workout = Workout.builder().userId(userId).build();
    final SetGroup setGroup = SetGroup.builder().workout(workout).build();
    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);
  }
}
