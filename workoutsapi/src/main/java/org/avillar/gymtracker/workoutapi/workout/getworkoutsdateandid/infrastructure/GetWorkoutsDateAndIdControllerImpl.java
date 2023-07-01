package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application.GetWorkoutsDateAndIdService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetWorkoutsDateAndIdControllerImpl implements GetWorkoutsDateAndIdController {

  private final GetWorkoutsDateAndIdService getWorkoutsDateAndIdService;

  @Override
  public ResponseEntity<GetWorkoutsDateAndIdResponseInfrastructure> execute(
      final UUID userId, final UUID exerciseId) throws IllegalAccessException {
    return ResponseEntity.ok(
        new GetWorkoutsDateAndIdResponseInfrastructure(
            getWorkoutsDateAndIdService.execute(userId, exerciseId)));
  }
}