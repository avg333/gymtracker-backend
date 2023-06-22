package org.avillar.gymtracker.workoutapi.set.infrastructure.get.set.mapper;

import org.avillar.gymtracker.workoutapi.set.application.get.set.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.set.model.GetSetResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetControllerMapper {

  GetSetResponseInfrastructure getResponse(GetSetResponseApplication getSetResponseApplication);
}
