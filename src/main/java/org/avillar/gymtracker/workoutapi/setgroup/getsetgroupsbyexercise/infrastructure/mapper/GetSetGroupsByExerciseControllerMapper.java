package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetSetGroupsByExerciseResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupsByExerciseControllerMapper {

  List<GetSetGroupsByExerciseResponse> map(
      List<GetSetGroupsByExerciseResponseApplication> getSetGroupsByExerciseResponseApplications);
}
