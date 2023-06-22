package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model.UpdateSetGroupSetsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets.model.UpdateSetGroupSetsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupSetsControllereMapper {

  UpdateSetGroupSetsResponseInfrastructure map(
      UpdateSetGroupSetsResponseApplication updateSetGroupSetsResponseApplication);
}
