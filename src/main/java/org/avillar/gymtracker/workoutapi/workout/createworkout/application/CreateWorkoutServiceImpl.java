package org.avillar.gymtracker.workoutapi.workout.createworkout.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.mapper.CreateWorkoutServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateWorkoutServiceImpl implements CreateWorkoutService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final CreateWorkoutServiceMapper createWorkoutServiceMapper;

  @Override
  public CreateWorkoutResponseApplication execute(
      final UUID userId, final CreateWorkoutRequestApplication createWorkoutRequestApplication)
      throws IllegalAccessException, DuplicatedWorkoutDateException {

    // TODO Verificar si el usuario existe

    if (workoutDao.existsWorkoutByUserAndDate(userId, createWorkoutRequestApplication.getDate())) {
      throw new DuplicatedWorkoutDateException(userId, createWorkoutRequestApplication.getDate());
    }

    final Workout workout = createWorkoutServiceMapper.map(createWorkoutRequestApplication);
    workout.setUserId(userId);

    authWorkoutsService.checkAccess(workout, AuthOperations.CREATE);

    workoutDao.save(workout);

    return createWorkoutServiceMapper.map(workout);
  }
}
