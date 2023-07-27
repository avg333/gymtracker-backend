package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateSetGroupServiceMapper {

  CreateSetGroupResponseApplication map(SetGroup postSetGroupResponse);

  SetGroup map(CreateSetGroupRequestApplication createSetGroupRequestApplication);
}
