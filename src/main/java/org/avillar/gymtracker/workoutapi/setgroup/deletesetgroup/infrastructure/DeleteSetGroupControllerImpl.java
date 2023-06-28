package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.application.DeleteSetGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteSetGroupControllerImpl implements DeleteSetGroupController {

  private final DeleteSetGroupService deleteSetGroupService;

  public ResponseEntity<Void> delete(final UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException {
    deleteSetGroupService.execute(setGroupId);
    return ResponseEntity.noContent().build();
  }
}
