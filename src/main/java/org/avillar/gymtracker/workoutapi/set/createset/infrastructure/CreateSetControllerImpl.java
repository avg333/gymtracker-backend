package org.avillar.gymtracker.workoutapi.set.createset.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.createset.application.CreateSetService;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper.CreateSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateSetControllerImpl implements CreateSetController {

  private final CreateSetService createSetService;
  private final CreateSetControllerMapper createSetControllerMapper;

  @Override
  public ResponseEntity<CreateSetResponse> execute(
      final UUID setGroupId, final CreateSetRequest createSetRequest)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        createSetControllerMapper.map(
            createSetService.execute(setGroupId, createSetControllerMapper.map(createSetRequest))));
  }
}
