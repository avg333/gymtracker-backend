package org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.model.GetExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.model.GetExerciseResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseControllerMapper {

  GetExerciseResponse getResponse(
      org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseResponse
          getExerciseResponse);

  List<GetExerciseResponse> getResponse(
      List<org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseResponse>
          getExerciseResponses);

  org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseRequest getRequest(
      GetExerciseRequest getExerciseRequest);
}
