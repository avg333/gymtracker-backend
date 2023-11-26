package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UpdateSetGroupSetsControllerMapper {

  List<UpdateSetGroupSetsResponse> map(List<Set> sets);
}
