package org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model.GetMuscleGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.model.GetMuscleGroupsInfrastructureResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleGroupControllerMapper {

  List<GetMuscleGroupsInfrastructureResponse> map(
      List<GetMuscleGroupsApplicationResponse> getMuscleGroupsApplicationResponses);
}
