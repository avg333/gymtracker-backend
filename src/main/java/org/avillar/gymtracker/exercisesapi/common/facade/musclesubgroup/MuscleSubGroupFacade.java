package org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;

public interface MuscleSubGroupFacade {

  List<MuscleSubGroup> getAllMuscleSubGroupsByMuscleGroupId(UUID muscleGroupId);
}
