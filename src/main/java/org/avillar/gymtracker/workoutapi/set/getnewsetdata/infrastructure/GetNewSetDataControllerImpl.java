package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.GetNewSetDataService;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.mapper.GetNewSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetNewSetDataControllerImpl implements GetNewSetDataController {

  private final GetNewSetDataService getNewSetDataService;
  private final GetNewSetDataControllerMapper getNewSetDataControllerMapper;

  @Override
  public GetNewSetDataResponse execute(final UUID setGroupId)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    return getNewSetDataControllerMapper.map(getNewSetDataService.execute(setGroupId));
  }
}
