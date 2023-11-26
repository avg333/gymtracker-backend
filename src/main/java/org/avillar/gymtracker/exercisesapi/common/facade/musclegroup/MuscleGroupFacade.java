package org.avillar.gymtracker.exercisesapi.common.facade.musclegroup;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;

public interface MuscleGroupFacade {

  List<MuscleGroup> getAllMuscleGroupsByMuscleSupGroupId(final UUID muscleSupGroupId);
}
