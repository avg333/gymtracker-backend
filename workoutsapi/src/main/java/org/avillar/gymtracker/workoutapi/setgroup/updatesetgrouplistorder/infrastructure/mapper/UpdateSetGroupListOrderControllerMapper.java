package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupListOrderControllerMapper {

  List<UpdateSetGroupListOrderResponseInfrastructure> map(
      List<UpdateSetGroupListOrderResponseApplication> updateSetGroupListOrderResponseApplications);
}
