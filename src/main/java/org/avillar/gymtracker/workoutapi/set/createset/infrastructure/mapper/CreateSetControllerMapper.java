package org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper;

import java.util.Date;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateSetControllerMapper {

  CreateSetResponse map(Set set);

  @Mapping(target = "completedAt", source = "completed", qualifiedByName = "mapCompletedAt")
  Set map(CreateSetRequest createSetRequest);

  @Named("mapCompletedAt")
  default Date mapCompletedAt(final Boolean completed) {
    return Boolean.TRUE.equals(completed) ? new Date() : null;
  }
}
