package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
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
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    return new UpdateWorkoutDescriptionResponse(
        updateWorkoutDescriptionService.execute(
            workoutId, updateWorkoutDescriptionRequest.description()));
  }
}
