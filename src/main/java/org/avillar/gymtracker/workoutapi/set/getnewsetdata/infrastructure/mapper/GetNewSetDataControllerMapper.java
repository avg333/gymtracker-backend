package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetNewSetDataControllerMapper {

  GetNewSetDataResponse map(Set set);
}
