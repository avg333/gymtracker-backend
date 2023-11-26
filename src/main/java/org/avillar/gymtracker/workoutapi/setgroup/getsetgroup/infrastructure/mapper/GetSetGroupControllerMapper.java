package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetSetGroupControllerMapper {

  GetSetGroupResponse map(SetGroup setGroup);
}
