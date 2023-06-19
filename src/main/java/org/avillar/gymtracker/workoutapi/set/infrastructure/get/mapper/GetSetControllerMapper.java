package org.avillar.gymtracker.workoutapi.set.infrastructure.get.mapper;

import org.avillar.gymtracker.workoutapi.set.application.get.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.model.GetSetResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetControllerMapper {

  GetSetResponseInfrastructure getResponse(GetSetResponseApplication getSetResponseApplication);
}
