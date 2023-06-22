package org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupServiceMapper {

  GetSetGroupResponseApplication map(SetGroup setGroup);
}
