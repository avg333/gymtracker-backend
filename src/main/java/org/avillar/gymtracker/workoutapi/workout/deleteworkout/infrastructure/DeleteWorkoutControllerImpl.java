package org.avillar.gymtracker.workoutapi.workout.deleteworkout.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.deleteworkout.application.DeleteWorkoutService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteWorkoutControllerImpl implements DeleteWorkoutController {

  private final DeleteWorkoutService deleteWorkoutService;

  public Void execute(final UUID workoutId) throws EntityNotFoundException, IllegalAccessException {
    deleteWorkoutService.execute(workoutId);
    return null;
  }
}
