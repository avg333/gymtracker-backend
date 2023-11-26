package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetExercisesByFilterControllerMapper {

  List<GetExercisesByFilterResponse> map(List<Exercise> exercises);

  GetExercisesByFilterRequestApplication map(
      GetExercisesByFilterRequest getExercisesByFilterRequest);
}
