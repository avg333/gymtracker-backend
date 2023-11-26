package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;

public interface GetAllMuscleGroupsByMuscleSupGroupService {

  List<MuscleGroup> execute(UUID muscleGroupId);
}
