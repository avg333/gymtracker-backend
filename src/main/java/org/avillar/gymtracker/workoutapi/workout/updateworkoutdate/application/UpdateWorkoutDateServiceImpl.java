package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application;

import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.exception.application.DuplicatedWorkoutDateException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateWorkoutDateServiceImpl implements UpdateWorkoutDateService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Date execute(final UUID workoutId, final Date date)
      throws EntityNotFoundException, DuplicatedWorkoutDateException, IllegalAccessException {

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
