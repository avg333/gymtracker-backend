package org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseApplicationRequest;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseApplicationResponse;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.model.GetExerciseInfrastructureRequest;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.model.GetExerciseInfrastructureResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseControllerMapper {

  GetExerciseInfrastructureResponse map(GetExerciseApplicationResponse getExerciseApplicationResponse);

  List<GetExerciseInfrastructureResponse> map(
      List<GetExerciseApplicationResponse> getExerciseApplicationResponses);

  GetExerciseApplicationRequest map(
      GetExerciseInfrastructureRequest getExerciseInfrastructureRequest);
}
