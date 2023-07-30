package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.exception.application.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application.UpdateWorkoutDateService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateRequest;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateWorkoutDateControllerImpl implements UpdateWorkoutDateController {

  private final UpdateWorkoutDateService updateWorkoutDateService;

  @Override
  public UpdateWorkoutDateResponse execute(
      final UUID workoutId, final UpdateWorkoutDateRequest updateWorkoutDateRequest)
      throws EntityNotFoundException, DuplicatedWorkoutDateException, IllegalAccessException {
    return new UpdateWorkoutDateResponse(
        updateWorkoutDateService.execute(workoutId, updateWorkoutDateRequest.getDate()));
  }
}
