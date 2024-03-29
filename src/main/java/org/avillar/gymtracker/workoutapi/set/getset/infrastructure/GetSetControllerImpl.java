package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.getset.application.GetSetService;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper.GetSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSetControllerImpl implements GetSetController {

  private final GetSetService getSetService;
  private final GetSetControllerMapper getSetControllerMapper;

  @Override
  public GetSetResponse execute(final UUID setId)
      throws SetNotFoundException, WorkoutIllegalAccessException {
    return getSetControllerMapper.map(getSetService.execute(setId));
  }
}
