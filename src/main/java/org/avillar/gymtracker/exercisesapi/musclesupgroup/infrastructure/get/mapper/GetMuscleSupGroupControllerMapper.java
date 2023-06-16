package org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.model.GetMuscleSupGroupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleSupGroupControllerMapper {

  GetMuscleSupGroupResponse getResponse(
      org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model
              .GetMuscleSupGroupResponse
          getMuscleSupGroupResponse);

  List<GetMuscleSupGroupResponse> getResponse(
      List<
              org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model
                  .GetMuscleSupGroupResponse>
          getMuscleSupGroupResponses);
}
