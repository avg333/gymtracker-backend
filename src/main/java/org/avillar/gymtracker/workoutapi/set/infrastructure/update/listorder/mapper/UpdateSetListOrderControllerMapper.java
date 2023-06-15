package org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.mapper;

import org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.model.UpdateSetListOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetListOrderControllerMapper {

  UpdateSetListOrderResponse updateResponse(
      org.avillar.gymtracker.workoutapi.set.application.update.listorder.model
              .UpdateSetListOrderResponse
          updateSetListOrderResponse);
}
