package org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.model.GetExercisesByFilterApplicationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExercisesByFilterServiceMapper {

  List<GetExercisesByFilterApplicationResponse> map(List<Exercise> exercises);
}
