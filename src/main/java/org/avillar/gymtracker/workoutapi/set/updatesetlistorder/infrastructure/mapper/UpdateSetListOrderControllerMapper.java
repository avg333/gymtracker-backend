package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetListOrderControllerMapper {

  List<UpdateSetListOrderResponse> map(
      List<UpdateSetListOrderResponseApplication> updateSetListOrderResponseApplications);
}
