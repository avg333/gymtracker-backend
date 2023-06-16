package org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.model.GetMuscleGroupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetMuscleGroupControllerMapper {

  GetMuscleGroupResponse getResponse(
      org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model.GetMuscleGroupResponse
          getMuscleGroupResponse);

  List<GetMuscleGroupResponse> getResponse(
      List<
              org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model
                  .GetMuscleGroupResponse>
          getMuscleGroupResponses);
}
