package org.avillar.gymtracker.workoutapi.workout.deleteworkout.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.deleteworkout.application.DeleteWorkoutService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteWorkoutControllerImpl implements DeleteWorkoutController {

  private final DeleteWorkoutService deleteWorkoutService;

  public Void execute(final UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    deleteWorkoutService.execute(workoutId);
    return null;
  }
}
