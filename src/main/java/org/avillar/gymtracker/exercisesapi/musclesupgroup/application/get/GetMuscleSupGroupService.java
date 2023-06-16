package org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model.GetMuscleSupGroupResponse;

public interface GetMuscleSupGroupService {

  GetMuscleSupGroupResponse getById(UUID muscleSupGroupId);

  List<GetMuscleSupGroupResponse> getAll();
}
