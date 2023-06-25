package org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model.GetMuscleSupGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.domain.MuscleSupGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleSupGroupApplicationMapper {

  List<GetMuscleSupGroupsApplicationResponse> map(List<MuscleSupGroup> muscleSupGroups);
}
