package org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateSetControllerMapper {

  CreateSetResponseInfrastructure map(CreateSetResponseApplication createSetResponseApplication);

  CreateSetRequestApplication map(CreateSetRequestInfrastructure createSetRequestInfrastructure);
}
