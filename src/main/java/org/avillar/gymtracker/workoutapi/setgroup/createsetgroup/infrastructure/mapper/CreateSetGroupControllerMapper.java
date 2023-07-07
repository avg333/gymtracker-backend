package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateSetGroupControllerMapper {

  CreateSetGroupResponse map(CreateSetGroupResponseApplication createSetGroupResponseApplication);

  CreateSetGroupRequestApplication map(CreateSetGroupRequest createSetGroupRequest);
}
