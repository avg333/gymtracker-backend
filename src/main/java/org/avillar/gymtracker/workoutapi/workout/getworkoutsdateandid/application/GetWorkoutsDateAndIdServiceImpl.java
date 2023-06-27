package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDateAndId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkoutsDateAndIdServiceImpl implements GetWorkoutsDateAndIdService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Map<Date, UUID> execute(final UUID userId, final UUID exerciseId)
      throws IllegalAccessException {

    checkWorkoutAccess(userId);

    final List<WorkoutDateAndId> workouts =
        Objects.nonNull(exerciseId)
            ? workoutDao.getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId)
            : workoutDao.getWorkoutsIdAndDatesByUser(userId);

    return workouts.stream()
        .collect(Collectors.toMap(WorkoutDateAndId::getDate, WorkoutDateAndId::getId));
  }

  private void checkWorkoutAccess(final UUID userId) {
    final Workout workout = new Workout();
    workout.setUserId(userId);
    authWorkoutsService.checkAccess(workout, AuthOperations.READ);
  }
}
