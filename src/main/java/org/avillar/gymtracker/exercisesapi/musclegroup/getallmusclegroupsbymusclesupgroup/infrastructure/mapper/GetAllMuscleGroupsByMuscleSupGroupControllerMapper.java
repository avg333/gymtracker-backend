package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model.GetAllMuscleGroupsByMuscleSupGroupResponseApplication;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.model.GetAllMuscleGroupsByMuscleSupGroupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetAllMuscleGroupsByMuscleSupGroupControllerMapper {

  List<GetAllMuscleGroupsByMuscleSupGroupResponse> map(
      List<GetAllMuscleGroupsByMuscleSupGroupResponseApplication>
          getAllMuscleGroupsByMuscleSupGroupResponseApplications);
}
