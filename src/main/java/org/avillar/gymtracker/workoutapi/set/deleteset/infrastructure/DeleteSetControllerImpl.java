package org.avillar.gymtracker.workoutapi.set.deleteset.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.deleteset.application.DeleteSetService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteSetControllerImpl implements DeleteSetController {

  private final DeleteSetService deleteSetService;

  @Override
  public Void execute(final UUID setId) throws SetNotFoundException, WorkoutIllegalAccessException {
    deleteSetService.execute(setId);
    return null;
  }
}
