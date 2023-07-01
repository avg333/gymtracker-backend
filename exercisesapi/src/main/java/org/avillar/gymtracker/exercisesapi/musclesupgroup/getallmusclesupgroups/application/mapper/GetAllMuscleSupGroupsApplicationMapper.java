package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetAllMuscleSupGroupsApplicationMapper {

  List<GetAllMuscleSupGroupsResponseApplication> map(List<MuscleSupGroup> muscleSupGroups);
}
