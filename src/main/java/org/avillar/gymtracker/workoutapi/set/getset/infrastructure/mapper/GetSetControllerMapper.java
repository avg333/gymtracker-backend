package org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetControllerMapper {

  GetSetResponse map(GetSetResponseApplication getSetResponseApplication);
}
