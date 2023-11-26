package org.avillar.gymtracker.workoutapi.set.updatesetdata.application.mapper;

import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UpdateSetDataServiceMapper {

  UpdateSetDataResponse map(Set set);
}
