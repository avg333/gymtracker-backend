package org.avillar.gymtracker.exercisesapi.musclegroup.application.get;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model.GetMuscleGroupsApplicationResponse;

public interface GetMuscleGroupService {

  List<GetMuscleGroupsApplicationResponse> execute(UUID muscleGroupId);
}
