package org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.model.GetMuscleSubGroupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleSubGroupControllerMapper {

  GetMuscleSubGroupResponse getResponse(
      org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model
              .GetMuscleSubGroupResponse
          getMuscleSubGroupResponse);

  List<GetMuscleSubGroupResponse> getResponse(
      List<
              org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model
                  .GetMuscleSubGroupResponse>
          getMuscleSubGroupResponses);
}
