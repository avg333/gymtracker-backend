package org.avillar.gymtracker.workoutapi.exercise.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseApplicationResponse;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseFacadeMapper {

  GetExerciseResponseFacade getResponse(
      GetExerciseApplicationResponse getExerciseApplicationResponse);

  List<GetExerciseResponseFacade> getResponse(List<GetExerciseApplicationResponse> getExerciseApplicationRespons);
}
