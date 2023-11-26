package org.avillar.gymtracker.exercisesapi.exercise.modifyexerciseuses.application;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;

public interface ModifyExerciseUsesService {

  List<ExerciseUses> execute(UUID userId, List<ExerciseUses> exerciseUses)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException;
}
