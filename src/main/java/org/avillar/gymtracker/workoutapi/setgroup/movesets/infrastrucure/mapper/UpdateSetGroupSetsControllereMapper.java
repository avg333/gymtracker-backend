package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.movesets.application.model.UpdateSetGroupSetsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupSetsControllereMapper {

  UpdateSetGroupSetsResponseInfrastructure map(
      UpdateSetGroupSetsResponseApplication updateSetGroupSetsResponseApplication);
}
