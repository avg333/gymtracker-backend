package org.avillar.gymtracker.workoutapi.exercise.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseFacadeMapper {

  GetExerciseResponseFacade getResponse(
      GetExercisesByIdsResponseApplication getExercisesByFilterApplicationResponse);

  List<GetExerciseResponseFacade> getResponse(
      List<GetExercisesByIdsResponseApplication> getExerciseApplicationRespons);
}
