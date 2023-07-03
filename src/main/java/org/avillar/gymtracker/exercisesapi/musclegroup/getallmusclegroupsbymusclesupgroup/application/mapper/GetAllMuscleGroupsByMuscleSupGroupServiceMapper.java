package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model.GetAllMuscleGroupsByMuscleSupGroupResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetAllMuscleGroupsByMuscleSupGroupServiceMapper {

  List<GetAllMuscleGroupsByMuscleSupGroupResponseApplication> map(List<MuscleGroup> muscleGroups);
}
