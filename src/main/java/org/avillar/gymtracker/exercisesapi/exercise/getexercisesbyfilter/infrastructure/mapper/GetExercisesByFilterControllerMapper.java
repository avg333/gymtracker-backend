package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExercisesByFilterControllerMapper {

  List<GetExercisesByFilterResponse> map(
      List<GetExercisesByFilterResponseApplication> getExercisesByFilterResponseApplication);

  GetExercisesByFilterRequestApplication map(
      GetExercisesByFilterRequest getExercisesByFilterRequest);
}
