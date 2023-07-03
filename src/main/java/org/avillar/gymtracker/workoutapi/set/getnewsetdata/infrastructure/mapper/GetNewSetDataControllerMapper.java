package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.model.GetNewSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetNewSetDataControllerMapper {

  GetNewSetDataResponseInfrastructure map(
      GetNewSetDataResponseApplication getNewSetDataResponseApplication);
}
