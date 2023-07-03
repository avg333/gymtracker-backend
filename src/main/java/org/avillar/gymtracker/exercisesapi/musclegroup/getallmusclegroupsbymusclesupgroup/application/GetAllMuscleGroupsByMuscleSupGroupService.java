package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model.GetAllMuscleGroupsByMuscleSupGroupResponseApplication;

public interface GetAllMuscleGroupsByMuscleSupGroupService {

  List<GetAllMuscleGroupsByMuscleSupGroupResponseApplication> execute(UUID muscleGroupId);
}
