package org.avillar.gymtracker.exercisesapi.workout.application.facade;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.GetSetGroupsByExerciseService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutRepositoryClientImpl implements WorkoutRepositoryClient {

  private final GetSetGroupsByExerciseService getSetGroupsByExerciseService;

  @Override
  public int getExerciseUsesNumberForUser(final UUID exerciseId, final UUID userId) {
    return getSetGroupsByExerciseService.execute(userId, exerciseId).size();
  }
}
