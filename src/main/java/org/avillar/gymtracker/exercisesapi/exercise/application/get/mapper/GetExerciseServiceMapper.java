package org.avillar.gymtracker.exercisesapi.exercise.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseApplicationResponse;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseServiceMapper {

  GetExerciseApplicationResponse map(Exercise exercise);

  List<GetExerciseApplicationResponse> map(List<Exercise> exercises);
}
