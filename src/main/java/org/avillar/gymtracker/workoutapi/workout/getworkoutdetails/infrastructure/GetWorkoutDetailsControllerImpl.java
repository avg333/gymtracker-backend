package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.GetWorkoutDetailsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper.GetWorkoutDetailsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetWorkoutDetailsControllerImpl implements GetWorkoutDetailsController {

  private final GetWorkoutDetailsService getWorkoutDetailsService;
  private final GetWorkoutDetailsControllerMapper getWorkoutDetailsControllerMapper;

  @Override
  public GetWorkoutDetailsResponseDto execute(final UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    return getWorkoutDetailsControllerMapper.map(getWorkoutDetailsService.execute(workoutId));
  }
}
