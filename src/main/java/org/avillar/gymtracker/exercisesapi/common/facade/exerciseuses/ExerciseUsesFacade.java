package org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;

public interface ExerciseUsesFacade {

  List<ExerciseUses> getExerciseUsesByExerciseIdAndUserId(List<UUID> exerciseIds, UUID userId);

  List<ExerciseUses> saveAllExerciseUses(List<ExerciseUses> exerciseUses);
}
