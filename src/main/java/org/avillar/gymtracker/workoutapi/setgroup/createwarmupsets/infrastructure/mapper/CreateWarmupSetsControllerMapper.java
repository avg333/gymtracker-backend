package org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.model.CreateWarmupSetsRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.model.CreateWarmupSetsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.model.CreateWarmupSetsRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateWarmupSetsControllerMapper {

  CreateWarmupSetsResponse map(
      CreateWarmupSetsResponseApplication createWarmupSetsResponseApplication);

  CreateWarmupSetsRequestApplication map(CreateWarmupSetsRequest createWarmupSetsRequest);
}
