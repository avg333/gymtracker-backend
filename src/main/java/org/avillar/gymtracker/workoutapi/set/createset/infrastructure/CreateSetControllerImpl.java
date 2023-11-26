package org.avillar.gymtracker.workoutapi.set.createset.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.createset.application.CreateSetService;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper.CreateSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateSetControllerImpl implements CreateSetController {

  private final CreateSetService createSetService;
  private final CreateSetControllerMapper createSetControllerMapper;

  @Override
  public CreateSetResponse execute(final UUID setGroupId, final CreateSetRequest createSetRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    return createSetControllerMapper.map(
        createSetService.execute(setGroupId, createSetControllerMapper.map(createSetRequest)));
  }
}
