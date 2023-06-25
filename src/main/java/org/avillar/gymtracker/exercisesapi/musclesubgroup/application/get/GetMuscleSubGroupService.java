package org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model.GetMuscleSubGroupsApplicationResponse;

public interface GetMuscleSubGroupService {

  List<GetMuscleSubGroupsApplicationResponse> execute(UUID muscleGroupId);
}
