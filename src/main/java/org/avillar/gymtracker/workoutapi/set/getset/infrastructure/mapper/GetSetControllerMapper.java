package org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetSetControllerMapper {

  GetSetResponse map(Set set);
}
