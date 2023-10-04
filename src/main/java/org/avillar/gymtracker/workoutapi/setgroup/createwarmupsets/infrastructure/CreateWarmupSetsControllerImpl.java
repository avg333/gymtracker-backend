package org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.CreateWarmupSetsService;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.mapper.CreateWarmupSetsControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.model.CreateWarmupSetsRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateWarmupSetsControllerImpl implements CreateWarmupSetsController {

  private final CreateWarmupSetsService createWarmupSetsService;
  private final CreateWarmupSetsControllerMapper createWarmupSetsControllerMapper;

  @Override
  public CreateWarmupSetsResponse execute(
      final UUID setGroupId, final CreateWarmupSetsRequest createWarmupSetsRequest) {
    return createWarmupSetsControllerMapper.map(
        createWarmupSetsService.execute(
            setGroupId, createWarmupSetsControllerMapper.map(createWarmupSetsRequest)));
  }
}
