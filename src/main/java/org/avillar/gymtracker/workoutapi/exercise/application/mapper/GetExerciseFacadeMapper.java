package org.avillar.gymtracker.workoutapi.exercise.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.model.GetExerciseResponse;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseFacadeMapper {

  GetExerciseResponseFacade getResponse(GetExerciseResponse getExerciseResponse);

  List<GetExerciseResponseFacade> getResponse(List<GetExerciseResponse> getExerciseResponses);
}
