package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupControllerMapper {

  GetSetGroupResponse map(GetSetGroupResponseApplication getSetGroupResponseApplication);
}
