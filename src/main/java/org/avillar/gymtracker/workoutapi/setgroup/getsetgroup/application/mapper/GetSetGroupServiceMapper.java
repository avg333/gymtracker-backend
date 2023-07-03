package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupServiceMapper {

  GetSetGroupResponseApplication map(SetGroup setGroup);
}
