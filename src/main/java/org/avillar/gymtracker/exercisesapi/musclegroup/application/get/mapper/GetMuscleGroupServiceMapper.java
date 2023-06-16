package org.avillar.gymtracker.exercisesapi.musclegroup.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model.GetMuscleGroupResponse;
import org.avillar.gymtracker.exercisesapi.musclegroup.domain.MuscleGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleGroupServiceMapper {

  GetMuscleGroupResponse getResponse(MuscleGroup muscleGroup);

  List<GetMuscleGroupResponse> getResponse(List<MuscleGroup> muscleGroups);
}
