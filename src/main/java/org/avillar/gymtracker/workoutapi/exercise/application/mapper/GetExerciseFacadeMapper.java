package org.avillar.gymtracker.workoutapi.exercise.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid.model.GetExerciseByIdApplicationResponse;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseFacadeMapper {

  GetExerciseResponseFacade getResponse(
      GetExerciseByIdApplicationResponse getExercisesByFilterApplicationResponse);

  List<GetExerciseResponseFacade> getResponse(
      List<GetExerciseByIdApplicationResponse> getExerciseApplicationRespons);
}
