package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.workout.WorkoutFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateWorkoutDescriptionServiceImpl implements UpdateWorkoutDescriptionService {

  private final WorkoutFacade workoutFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public String execute(final UUID workoutId, final String description)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {

    final Workout workout = workoutFacade.getWorkout(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.UPDATE);

    if (StringUtils.equals(workout.getDescription(), description)) {
      return description;
    }

    workout.setDescription(description);

    return workoutFacade.saveWorkout(workout).getDescription();
  }
}
