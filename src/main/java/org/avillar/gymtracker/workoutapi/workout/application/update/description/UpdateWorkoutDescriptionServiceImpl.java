package org.avillar.gymtracker.workoutapi.workout.application.update.description;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

// RDY
@Service
@RequiredArgsConstructor
public class UpdateWorkoutDescriptionServiceImpl implements UpdateWorkoutDescriptionService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public String update(final UUID workoutId, final String description) {

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
