package org.avillar.gymtracker.workoutapi.set.deleteset.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.deleteset.application.DeleteSetService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteSetControllerImpl implements DeleteSetController {

  private final DeleteSetService deleteSetService;

  @Override
  public Void execute(final UUID setId) throws EntityNotFoundException, IllegalAccessException {
    deleteSetService.execute(setId);
    return null;
  }
}
