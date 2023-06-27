package org.avillar.gymtracker.workoutapi.workout.getworkout.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.mapper.GetWorkoutSimpleServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutSimpleResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkoutSimpleServiceImpl implements GetWorkoutSimpleService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetWorkoutSimpleServiceMapper getWorkoutSimpleServiceMapper;

  @Override
  public GetWorkoutSimpleResponseApplication execute(final UUID workoutId) {
    final Workout workout =
        workoutDao
            .findById(workoutId)
            .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    return getWorkoutSimpleServiceMapper.map(workout);
  }
}
