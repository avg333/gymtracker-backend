package org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyfilters.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.model.GetExercisesByFilterApplicationRequest;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.model.GetExercisesByFilterApplicationResponse;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyfilters.model.GetExerciseInfrastructureRequest;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyfilters.model.GetExerciseInfrastructureResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExercisesByFilterControllerMapper {

  List<GetExerciseInfrastructureResponse> map(
      List<GetExercisesByFilterApplicationResponse> getExercisesByFilterApplicationResponse);

  GetExercisesByFilterApplicationRequest map(
      GetExerciseInfrastructureRequest getExerciseInfrastructureRequest);
}
