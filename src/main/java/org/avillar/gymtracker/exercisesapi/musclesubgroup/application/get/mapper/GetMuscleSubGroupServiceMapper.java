package org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model.GetMuscleSubGroupsApplicationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleSubGroupServiceMapper {

  List<GetMuscleSubGroupsApplicationResponse> map(List<MuscleSubGroup> muscleSubGroups);
}
