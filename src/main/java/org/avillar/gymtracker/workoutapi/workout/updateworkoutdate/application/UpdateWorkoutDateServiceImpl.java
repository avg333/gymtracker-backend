package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateWorkoutDateServiceImpl implements UpdateWorkoutDateService {

  private final WorkoutFacade workoutFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public LocalDate execute(final UUID workoutId, final LocalDate date)
      throws WorkoutNotFoundException,
          WorkoutForDateAlreadyExistsException,
          WorkoutIllegalAccessException {

    final Workout workout = workoutFacade.getWorkout(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.UPDATE);

    if (nonNull(workout.getDate()) && workout.getDate().equals(date)) {
      return date;
    }

    if (workoutFacade.existsWorkoutByUserAndDate(workout.getUserId(), date)) {
      throw new WorkoutForDateAlreadyExistsException(workout.getUserId(), date);
    }

    workout.setDate(date);

    return workoutFacade.saveWorkout(workout).getDate();
  }
}
