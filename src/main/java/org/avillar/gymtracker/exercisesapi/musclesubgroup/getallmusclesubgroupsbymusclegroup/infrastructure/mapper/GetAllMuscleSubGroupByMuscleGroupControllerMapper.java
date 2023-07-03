package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.model.GetAllMuscleSubGroupByMuscleGroupResponseApplication;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetAllMuscleSubGroupByMuscleGroupControllerMapper {
  List<GetAllMuscleSubGroupByMuscleGroupResponseInfrastructure> map(
      List<GetAllMuscleSubGroupByMuscleGroupResponseApplication>
          getAllMuscleSubGroupByMuscleGroupResponseApplications);
}
