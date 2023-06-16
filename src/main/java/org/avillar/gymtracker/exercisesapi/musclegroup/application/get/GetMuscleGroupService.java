package org.avillar.gymtracker.exercisesapi.musclegroup.application.get;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model.GetMuscleGroupResponse;

public interface GetMuscleGroupService {
  GetMuscleGroupResponse getById(UUID muscleGroupId);

  List<GetMuscleGroupResponse> getAll();
}
