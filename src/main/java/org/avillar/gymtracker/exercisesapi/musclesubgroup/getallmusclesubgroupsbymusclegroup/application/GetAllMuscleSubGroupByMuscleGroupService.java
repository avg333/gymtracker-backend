package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.model.GetAllMuscleSubGroupByMuscleGroupResponseApplication;

public interface GetAllMuscleSubGroupByMuscleGroupService {

  List<GetAllMuscleSubGroupByMuscleGroupResponseApplication> execute(UUID muscleGroupId);
}
