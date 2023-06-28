package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateSetGroupControllerMapper {

  CreateSetGroupResponseInfrastructure map(
      CreateSetGroupResponseApplication createSetGroupResponseApplication);

  CreateSetGroupRequestApplication map(
      CreateSetGroupRequestInfrastructure createSetGroupRequestInfrastructure);
}
