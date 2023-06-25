package org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model.GetMuscleSupGroupsApplicationResponse;

public interface GetMuscleSupGroupService {

  List<GetMuscleSupGroupsApplicationResponse> execute();
}
