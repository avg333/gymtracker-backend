package org.avillar.gymtracker.workoutapi.workout.application.update.date;

import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDateWorkoutServiceImpl implements UpdateDateWorkoutService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Date update(final UUID workoutId, final Date date) {

    final Workout workout =
        workoutDao
            .findById(workoutId)
            .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));

    authWorkoutsService.checkAccess(workout, AuthOperations.UPDATE);

    if (workout.getDate().equals(date)) {
      return workout.getDate();
    }

    if (workoutDao.existsWorkoutByUserAndDate(workout.getUserId(), date)) {
      throw new DuplicatedWorkoutDateException(workout.getUserId(), date);
    }

    workout.setDate(date);

    workoutDao.save(workout);

    return workout.getDate();
  }
}
