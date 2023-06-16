package org.avillar.gymtracker.exercisesapi.exercise.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseResponse;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseServiceMapper {

  GetExerciseResponse getResponse(Exercise exercise);

  List<GetExerciseResponse> getResponse(List<Exercise> exercises);
}
