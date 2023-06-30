package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetListOrderControllerMapper {

  List<UpdateSetListOrderResponseInfrastructure> map(
      List<UpdateSetListOrderResponseApplication> updateSetListOrderResponseApplications);
}
