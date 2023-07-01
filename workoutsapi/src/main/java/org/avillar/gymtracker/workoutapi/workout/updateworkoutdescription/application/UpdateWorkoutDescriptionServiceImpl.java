package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateWorkoutDescriptionServiceImpl implements UpdateWorkoutDescriptionService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public String execute(final UUID workoutId, final String description)
      throws EntityNotFoundException, IllegalAccessException {

    final Workout workout =
        workoutDao
            .findById(workoutId)
            .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));

    authWorkoutsService.checkAccess(workout, AuthOperations.UPDATE);

    if (StringUtils.equals(workout.getDescription(), description)) {
      return workout.getDescription();
    }

    workout.setDescription(description);

    workoutDao.save(workout);

    return workout.getDescription();
  }
}