package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.GetNewSetDataService;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.mapper.GetNewSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetNewSetDataControllerImpl implements GetNewSetDataController {

  private final GetNewSetDataService getNewSetDataService;
  private final GetNewSetDataControllerMapper getNewSetDataControllerMapper;

  @Override
  public ResponseEntity<GetNewSetDataResponse> execute(final UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        getNewSetDataControllerMapper.map(getNewSetDataService.execute(setGroupId)));
  }
}
