package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateSetGroupControllerMapper {

  CreateSetGroupResponse map(SetGroup setGroup);

  SetGroup map(CreateSetGroupRequest createSetGroupRequest);
}
