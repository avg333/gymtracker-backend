package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.GetWorkoutDetailsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper.GetWorkoutDetailsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetWorkoutDetailsControllerImpl implements GetWorkoutDetailsController {

  private final GetWorkoutDetailsService getWorkoutDetailsService;
  private final GetWorkoutDetailsControllerMapper getWorkoutDetailsControllerMapper;

  @Override
  public ResponseEntity<GetWorkoutDetailsResponse> execute(final UUID workoutId) {
    return ResponseEntity.ok(
        getWorkoutDetailsControllerMapper.map(getWorkoutDetailsService.execute(workoutId)));
  }
}
