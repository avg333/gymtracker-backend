package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.model.UpdateSetGroupListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.model.UpdateSetGroupListOrderResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupListOrderControllerMapper {

  UpdateSetGroupListOrderResponseInfrastructure updateResponse(
      UpdateSetGroupListOrderResponseApplication updateSetGroupListOrderResponseApplication);
}
