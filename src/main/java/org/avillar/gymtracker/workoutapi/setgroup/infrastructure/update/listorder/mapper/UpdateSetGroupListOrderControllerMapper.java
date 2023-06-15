package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.model.UpdateSetGroupListOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupListOrderControllerMapper {

  org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.model
          .UpdateSetGroupListOrderResponse
      updateResponse(UpdateSetGroupListOrderResponse updateSetGroupListOrderResponse);
}
