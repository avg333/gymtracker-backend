package org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.mapper;

import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetDataControllerMapper {

  UpdateSetDataResponseInfrastructure map(
      UpdateSetDataResponseApplication updateSetDataResponseApplication);

  UpdateSetDataRequestApplication map(
      UpdateSetDataRequestInfrastructure updateSetDataRequestInfrastructure);
}
