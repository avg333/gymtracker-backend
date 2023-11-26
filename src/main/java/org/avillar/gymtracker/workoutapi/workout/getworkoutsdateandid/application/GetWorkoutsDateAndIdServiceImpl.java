package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkoutsDateAndIdServiceImpl implements GetWorkoutsDateAndIdService {

  private final WorkoutFacade workoutFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Map<LocalDate, UUID> execute(final UUID userId, final UUID exerciseId)
      throws WorkoutIllegalAccessException {

    checkWorkoutAccess(userId);

    return Objects.nonNull(exerciseId)
        ? workoutFacade.getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId)
        : workoutFacade.getWorkoutsIdAndDatesByUser(userId);
  }

  private void checkWorkoutAccess(final UUID userId) throws WorkoutIllegalAccessException {
    authWorkoutsService.checkAccess(Workout.builder().userId(userId).build(), AuthOperations.READ);
  }
}
