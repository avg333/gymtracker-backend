package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application.DeleteSetGroupService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteSetGroupControllerImpl implements DeleteSetGroupController {

  private final DeleteSetGroupService deleteSetGroupService;

  public Void execute(final UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException {
    deleteSetGroupService.execute(setGroupId);
    return null;
  }
}
