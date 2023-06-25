package org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model.GetMuscleSubGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.model.GetMuscleSubGroupsInfrastructureResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleSubGroupControllerMapper {
  List<GetMuscleSubGroupsInfrastructureResponse> map(
      List<GetMuscleSubGroupsApplicationResponse> getMuscleSubGroupsApplicationResponses);
}
