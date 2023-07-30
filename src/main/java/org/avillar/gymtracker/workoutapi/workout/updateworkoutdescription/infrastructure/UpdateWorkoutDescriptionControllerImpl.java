package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application.UpdateWorkoutDescriptionService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionRequest;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateWorkoutDescriptionControllerImpl implements UpdateWorkoutDescriptionController {

  private final UpdateWorkoutDescriptionService updateWorkoutDescriptionService;

  public UpdateWorkoutDescriptionResponse execute(
      final UUID workoutId, final UpdateWorkoutDescriptionRequest updateWorkoutDescriptionRequest)
      throws EntityNotFoundException, IllegalAccessException {
    return new UpdateWorkoutDescriptionResponse(
        updateWorkoutDescriptionService.execute(
            workoutId, updateWorkoutDescriptionRequest.getDescription()));
  }
}
