package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetListOrderControllerMapper {

  UpdateSetListOrderResponseInfrastructure map(
      UpdateSetListOrderResponseApplication updateSetListOrderResponseApplication);
}
