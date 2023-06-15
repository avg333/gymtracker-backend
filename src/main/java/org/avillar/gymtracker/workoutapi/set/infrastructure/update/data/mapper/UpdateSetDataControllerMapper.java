package org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.mapper;

import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetDataControllerMapper {

  org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataResponse
      updateResponse(UpdateSetDataResponse updateSetDataResponse);

  UpdateSetDataRequest updateRequest(
      org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataRequest
          updateSetDataRequest);
}
