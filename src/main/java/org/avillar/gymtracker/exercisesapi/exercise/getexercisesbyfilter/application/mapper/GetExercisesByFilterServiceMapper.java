package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExercisesByFilterServiceMapper {

  List<GetExercisesByFilterResponseApplication> map(List<Exercise> exercises);
}
