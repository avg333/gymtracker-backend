package org.avillar.gymtracker.workoutapi.set.infrastructure.get.mapper;

import org.avillar.gymtracker.workoutapi.set.application.get.model.GetSetResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetControllerMapper {

  org.avillar.gymtracker.workoutapi.set.infrastructure.get.model.GetSetResponse getResponse(
      GetSetResponse getSetResponse);
}
