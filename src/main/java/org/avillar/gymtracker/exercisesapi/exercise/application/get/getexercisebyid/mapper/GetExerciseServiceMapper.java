package org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid.model.GetExerciseByIdApplicationResponse;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseServiceMapper {

  GetExerciseByIdApplicationResponse map(Exercise exercise);

  List<GetExerciseByIdApplicationResponse> map(List<Exercise> exercise);
}
