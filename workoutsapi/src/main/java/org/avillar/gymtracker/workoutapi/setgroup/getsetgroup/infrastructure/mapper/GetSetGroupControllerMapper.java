package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupControllerMapper {

  GetSetGroupResponseInfrastructure map(
      GetSetGroupResponseApplication getSetGroupResponseApplication);
}
