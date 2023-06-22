package org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata.mapper;

import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.model.GetNewSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata.model.GetNewSetDataResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetNewSetDataControllerMapper {

  GetNewSetDataResponseInfrastructure map(
      GetNewSetDataResponseApplication getNewSetDataResponseApplication);
}
