package org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model.GetMuscleSupGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.model.GetMuscleSupGroupsInfrastructureResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleSupGroupControllerMapper {

  List<GetMuscleSupGroupsInfrastructureResponse> map(
      List<GetMuscleSupGroupsApplicationResponse> getMuscleSupGroupsApplicationResponses);
}
