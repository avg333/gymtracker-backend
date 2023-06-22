package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.setgroup.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.setgroup.model.GetSetGroupResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupControllerMapper {

  GetSetGroupResponseInfrastructure map(
      GetSetGroupResponseApplication getSetGroupResponseApplication);
}
