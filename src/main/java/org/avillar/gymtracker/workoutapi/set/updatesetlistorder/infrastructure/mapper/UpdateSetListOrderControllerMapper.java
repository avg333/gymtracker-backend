package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UpdateSetListOrderControllerMapper {

  List<UpdateSetListOrderResponse> map(List<Set> sets);
}
