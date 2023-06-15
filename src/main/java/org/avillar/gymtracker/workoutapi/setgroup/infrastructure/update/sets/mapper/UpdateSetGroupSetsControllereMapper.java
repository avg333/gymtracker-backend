package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model.UpdateSetGroupSetsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupSetsControllereMapper {

  org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets.model
          .UpdateSetGroupSetsResponse
      updateResponse(UpdateSetGroupSetsResponse updateSetGroupSetsResponse);
}
