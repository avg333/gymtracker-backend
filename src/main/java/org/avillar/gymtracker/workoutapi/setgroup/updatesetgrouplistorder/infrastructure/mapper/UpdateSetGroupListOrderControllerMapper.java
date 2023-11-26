package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UpdateSetGroupListOrderControllerMapper {

  List<UpdateSetGroupListOrderResponse> map(List<SetGroup> setGroups);
}
