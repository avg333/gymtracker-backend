package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetDataControllerMapper {

  UpdateSetDataResponse map(UpdateSetDataResponseApplication updateSetDataResponseApplication);

  UpdateSetDataRequestApplication map(UpdateSetDataRequest updateSetDataRequest);
}
