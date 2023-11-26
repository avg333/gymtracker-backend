package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application.DeleteExerciseService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteExerciseControllerImpl implements DeleteExerciseController {

  private final DeleteExerciseService deleteExerciseService;

  @Override
  public Void execute(final UUID exerciseId)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {
    deleteExerciseService.execute(exerciseId);
    return null;
  }
}
