package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application.UpdateWorkoutDateService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateWorkoutDateControllerImpl implements UpdateWorkoutDateController {

  private final UpdateWorkoutDateService updateWorkoutDateService;

  @Override
  public ResponseEntity<UpdateWorkoutDateResponseInfrastructure> execute(
      final UUID workoutId,
      final UpdateWorkoutDateRequestInfrastructure updateWorkoutDateRequestInfrastructure)
      throws EntityNotFoundException, DuplicatedWorkoutDateException, IllegalAccessException {
    return ResponseEntity.ok(
        new UpdateWorkoutDateResponseInfrastructure(
            updateWorkoutDateService.execute(
                workoutId, updateWorkoutDateRequestInfrastructure.getDate())));
  }
}
