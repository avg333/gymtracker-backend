package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.model.GetAllMuscleSubGroupByMuscleGroupResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetAllMuscleSubGroupByMuscleGroupServiceMapper {

  List<GetAllMuscleSubGroupByMuscleGroupResponseApplication> map(
      List<MuscleSubGroup> muscleSubGroups);
}
