package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;

public interface GetAllMuscleSubGroupByMuscleGroupService {

  List<MuscleSubGroup> execute(UUID muscleGroupId);
}
