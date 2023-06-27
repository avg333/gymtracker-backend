package org.avillar.gymtracker.workoutapi.workout.deleteworkout.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteWorkoutServiceImpl implements DeleteWorkoutService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public void execute(final UUID workoutId)
      throws EntityNotFoundException, IllegalArgumentException {
    final Workout workout =
        workoutDao
            .findById(workoutId)
            .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));

    authWorkoutsService.checkAccess(workout, AuthOperations.DELETE);

    workoutDao.deleteById(workoutId);
  }
}
