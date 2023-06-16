package org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model.GetMuscleSubGroupResponse;

public interface GetMuscleSubGroupService {

  GetMuscleSubGroupResponse getById(UUID muscleSubGroupId);

  List<GetMuscleSubGroupResponse> getAllByMuscleGroupId(UUID muscleGroupId);
}
