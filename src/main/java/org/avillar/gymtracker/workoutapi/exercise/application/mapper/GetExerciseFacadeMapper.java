package org.avillar.gymtracker.workoutapi.exercise.application.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseFacadeMapper {

  GetExerciseResponse getResponse(
      org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseResponse
          getExerciseResponse);

  List<GetExerciseResponse> getResponse(
      List<org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseResponse>
          getExerciseResponses);
}
