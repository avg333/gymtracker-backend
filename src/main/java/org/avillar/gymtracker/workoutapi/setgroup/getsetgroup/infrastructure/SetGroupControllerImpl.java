package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.GetSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper.GetSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SetGroupControllerImpl implements SetGroupController {

  private final GetSetGroupService getSetGroupService;
  private final GetSetGroupControllerMapper getSetGroupControllerMapper;

  @Override
  public ResponseEntity<GetSetGroupResponseInfrastructure> get(final UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        getSetGroupControllerMapper.map(getSetGroupService.execute(setGroupId)));
  }
}