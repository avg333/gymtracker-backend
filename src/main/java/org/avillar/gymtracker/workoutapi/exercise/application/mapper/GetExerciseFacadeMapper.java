package org.avillar.gymtracker.workoutapi.exercise.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseFacadeMapper {

  GetExerciseResponseFacade map(
      GetExercisesByIdsResponseApplication getExercisesByFilterApplicationResponseApplication);

  List<GetExerciseResponseFacade> map(
      List<GetExercisesByIdsResponseApplication> getExerciseApplicationResponseApplications);
}
