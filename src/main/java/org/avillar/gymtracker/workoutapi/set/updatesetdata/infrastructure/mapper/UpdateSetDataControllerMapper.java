package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponse;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequestDto;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UpdateSetDataControllerMapper {

  UpdateSetDataResponseDto map(UpdateSetDataResponse updateSetDataResponse);

  UpdateSetDataRequest map(UpdateSetDataRequestDto updateSetDataRequestDto);
}
