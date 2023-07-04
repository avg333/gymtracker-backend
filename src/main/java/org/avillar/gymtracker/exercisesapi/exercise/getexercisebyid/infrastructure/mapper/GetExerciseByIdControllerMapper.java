package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.mapper;

import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseByIdControllerMapper {

  GetExerciseByIdResponseInfrastructure map(
      GetExerciseByIdResponseApplication getExerciseByIdResponseApplication);
}
