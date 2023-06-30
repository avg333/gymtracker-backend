package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExercisesByIdsServiceMapper {

  List<GetExercisesByIdsResponseApplication> map(List<Exercise> exercise);
}
