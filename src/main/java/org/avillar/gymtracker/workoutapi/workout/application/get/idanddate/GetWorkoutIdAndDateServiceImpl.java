package org.avillar.gymtracker.workoutapi.workout.application.get.idanddate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDateAndId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkoutIdAndDateServiceImpl implements GetWorkoutIdAndDateService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Map<Date, UUID> getAllUserWorkoutDates(final UUID userId) {

    checkWorkoutAccess(userId);

    return workoutDao.getWorkoutsIdAndDatesByUser(userId).stream()
        .collect(Collectors.toMap(WorkoutDateAndId::getDate, WorkoutDateAndId::getId));
  }

  @Override
  public Map<Date, UUID> getAllUserWorkoutsWithExercise(final UUID userId, final UUID exerciseId) {

    checkWorkoutAccess(userId);

    return workoutDao.getWorkoutsIdAndDatesByUserAndExercise(userId, exerciseId).stream()
        .collect(Collectors.toMap(WorkoutDateAndId::getDate, WorkoutDateAndId::getId));
  }

  private void checkWorkoutAccess(final UUID userId) {
    final Workout workout = new Workout();
    workout.setUserId(userId);
    authWorkoutsService.checkAccess(workout, AuthOperations.READ);
  }
}
