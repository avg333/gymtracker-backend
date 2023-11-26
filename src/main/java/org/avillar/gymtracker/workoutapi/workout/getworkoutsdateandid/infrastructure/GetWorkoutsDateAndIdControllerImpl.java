package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application.GetWorkoutsDateAndIdService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.mapper.GetWorkoutsDateAndIdControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetWorkoutsDateAndIdControllerImpl implements GetWorkoutsDateAndIdController {

  private final GetWorkoutsDateAndIdService getWorkoutsDateAndIdService;
  private final GetWorkoutsDateAndIdControllerMapper getWorkoutsDateAndIdControllerMapper;

  @Override
  public GetWorkoutsDateAndIdResponse execute(final UUID userId, final UUID exerciseId)
      throws WorkoutIllegalAccessException {
    return getWorkoutsDateAndIdControllerMapper.map(
        getWorkoutsDateAndIdService.execute(userId, exerciseId));
  }
}
