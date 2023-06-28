package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupListOrderControllerMapper {

  UpdateSetGroupListOrderResponseInfrastructure map(
      UpdateSetGroupListOrderResponseApplication updateSetGroupListOrderResponseApplication);
}
