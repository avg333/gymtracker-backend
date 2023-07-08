package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.mapper;

import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseByIdControllerMapper {

  GetExerciseByIdResponse map(
      GetExerciseByIdResponseApplication getExerciseByIdResponseApplication);
}
