package org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyid.mapper;

import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid.model.GetExerciseByIdApplicationResponse;
import org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.getexercisebyid.model.GetExerciseByIdInfrastructureResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseByIdControllerMapper {

  GetExerciseByIdInfrastructureResponse map(
      GetExerciseByIdApplicationResponse getExerciseByIdApplicationResponse);
}
