package org.avillar.gymtracker.workoutapi.workout.getworkout.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.mapper.GetWorkoutServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkoutServiceImpl implements GetWorkoutService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetWorkoutServiceMapper getWorkoutServiceMapper;

  @Override
  public GetWorkoutResponseApplication execute(final UUID workoutId)
      throws EntityNotFoundException, IllegalAccessException {
    final Workout workout =
        workoutDao
            .findById(workoutId)
            .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    return getWorkoutServiceMapper.map(workout);
  }
}
