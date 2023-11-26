package org.avillar.gymtracker.exercisesapi.common.facade.workout;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.GetSetGroupsByExerciseService;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WorkoutsFacadeImpl implements WorkoutsFacade {

  private final GetSetGroupsByExerciseService getSetGroupsByExerciseService;

  @Override
  public int getExerciseUsesNumberForUser(final UUID exerciseId, final UUID userId)
      throws WorkoutIllegalAccessException {
    return getSetGroupsByExerciseService.execute(userId, exerciseId).size();
  }
}
