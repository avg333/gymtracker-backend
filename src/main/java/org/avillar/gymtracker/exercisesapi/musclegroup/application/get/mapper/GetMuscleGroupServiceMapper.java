package org.avillar.gymtracker.exercisesapi.musclegroup.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model.GetMuscleGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclegroup.domain.MuscleGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleGroupServiceMapper {

  List<GetMuscleGroupsApplicationResponse> map(List<MuscleGroup> muscleGroups);
}
