package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper.UpdateSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetDataControllerImpl implements UpdateSetDataController {

  private final UpdateSetDataService updateSetDataService;
  private final UpdateSetDataControllerMapper updateSetDataControllerMapper;

  @Override
  public ResponseEntity<UpdateSetDataResponse> execute(
      final UUID setId, final UpdateSetDataRequest updateSetDataRequest)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        updateSetDataControllerMapper.map(
            updateSetDataService.execute(
                setId, updateSetDataControllerMapper.map(updateSetDataRequest))));
  }
}
