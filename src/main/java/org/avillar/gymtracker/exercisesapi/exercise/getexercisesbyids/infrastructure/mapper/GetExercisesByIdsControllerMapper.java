package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExercisesByIdsControllerMapper {

  List<GetExercisesByIdsResponseInfrastructure> map(
      List<GetExercisesByIdsResponseApplication> getExercisesByIdsResponseApplications);
}
