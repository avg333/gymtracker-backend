package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.mapper;

import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseByIdServiceMapper {

  GetExerciseByIdResponseApplication map(Exercise exercise);
}
