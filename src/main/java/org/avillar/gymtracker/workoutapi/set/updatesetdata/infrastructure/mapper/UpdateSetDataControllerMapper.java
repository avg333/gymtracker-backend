package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetDataControllerMapper {

  UpdateSetDataResponseInfrastructure map(
      UpdateSetDataResponseApplication updateSetDataResponseApplication);

  UpdateSetDataRequestApplication map(
      UpdateSetDataRequestInfrastructure updateSetDataRequestInfrastructure);
}
