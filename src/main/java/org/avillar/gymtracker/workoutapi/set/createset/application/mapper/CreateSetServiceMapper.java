package org.avillar.gymtracker.workoutapi.set.createset.application.mapper;

import java.util.Date;
import org.apache.commons.lang3.BooleanUtils;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateSetServiceMapper {

  CreateSetResponseApplication map(Set set);

  Set map(CreateSetRequestApplication createSetRequestApplication);

  @AfterMapping
  default void mapCompleted(
      final @MappingTarget Set set, final CreateSetRequestApplication createSetRequestApplication) {
    if (BooleanUtils.isTrue(createSetRequestApplication.getCompleted())) {
      set.setCompletedAt(new Date());
    }
  }
}
