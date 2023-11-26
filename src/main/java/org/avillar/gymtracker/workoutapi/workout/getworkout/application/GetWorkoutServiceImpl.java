package org.avillar.gymtracker.workoutapi.workout.getworkout.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkoutServiceImpl implements GetWorkoutService {

  private final WorkoutFacade workoutFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Workout execute(final UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final Workout workout = workoutFacade.getWorkout(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    return workout;
  }
}
