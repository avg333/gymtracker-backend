package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.GetWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper.GetWorkoutControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetWorkoutControllerImpl implements GetWorkoutController {

  private final GetWorkoutService getWorkoutServiceService;
  private final GetWorkoutControllerMapper getWorkoutControllerMapper;

  public GetWorkoutResponse execute(final UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    return getWorkoutControllerMapper.map(getWorkoutServiceService.execute(workoutId));
  }
}
