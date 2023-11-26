package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure.model.GetAllMuscleSupGroupsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetAllMuscleSupGroupsControllerMapper {

  List<GetAllMuscleSupGroupsResponse> map(List<MuscleSupGroup> muscleSupGroups);
}
