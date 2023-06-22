package org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.mapper;

import org.avillar.gymtracker.workoutapi.set.application.update.listorder.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.model.UpdateSetListOrderResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetListOrderControllerMapper {

  UpdateSetListOrderResponseInfrastructure map(
      UpdateSetListOrderResponseApplication updateSetListOrderResponseApplication);
}
