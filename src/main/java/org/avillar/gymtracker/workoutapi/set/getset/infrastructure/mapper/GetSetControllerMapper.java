package org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetControllerMapper {

  GetSetResponseInfrastructure map(GetSetResponseApplication getSetResponseApplication);
}
