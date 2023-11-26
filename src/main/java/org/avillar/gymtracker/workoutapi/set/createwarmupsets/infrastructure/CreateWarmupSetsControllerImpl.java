package org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.application.CreateWarmupSetsService;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.mapper.CreateWarmupSetsControllerMapper;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model.CreateWarmupSetsRequest;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateWarmupSetsControllerImpl implements CreateWarmupSetsController {

  private final CreateWarmupSetsService createWarmupSetsService;
  private final CreateWarmupSetsControllerMapper createWarmupSetsControllerMapper;

  @Override
  public List<CreateWarmupSetsResponse> execute(
      final UUID setGroupId, final CreateWarmupSetsRequest createWarmupSetsRequest)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    return createWarmupSetsControllerMapper.map(
        createWarmupSetsService.execute(setGroupId, null, null));
  }
}
