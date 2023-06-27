package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application.UpdateWorkoutDescriptionService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateWorkoutDescriptionControllerImpl implements UpdateWorkoutDescriptionController {

  private final UpdateWorkoutDescriptionService updateWorkoutDescriptionService;

  public ResponseEntity<UpdateWorkoutDescriptionResponseInfrastructure> execute(
      final UUID workoutId,
      final UpdateWorkoutDescriptionRequestInfrastructure
          updateWorkoutDescriptionRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        new UpdateWorkoutDescriptionResponseInfrastructure(
            updateWorkoutDescriptionService.execute(
                workoutId, updateWorkoutDescriptionRequestInfrastructure.getDescription())));
  }
}
