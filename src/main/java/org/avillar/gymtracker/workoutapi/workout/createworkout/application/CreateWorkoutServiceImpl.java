package org.avillar.gymtracker.workoutapi.workout.createworkout.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateWorkoutServiceImpl implements CreateWorkoutService {

  private final WorkoutFacade workoutFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Workout execute(final UUID userId, final Workout workout)
      throws WorkoutIllegalAccessException, WorkoutForDateAlreadyExistsException {

    workout.setUserId(userId);

    authWorkoutsService.checkAccess(workout, AuthOperations.CREATE);

    if (workoutFacade.existsWorkoutByUserAndDate(userId, workout.getDate())) {
      throw new WorkoutForDateAlreadyExistsException(userId, workout.getDate());
    }

    return workoutFacade.saveWorkout(workout);
  }
}
