package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application.DeleteExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteExerciseControllerImpl implements DeleteExerciseController {

  private final DeleteExerciseService deleteExerciseService;

  @Override
  public ResponseEntity<Void> execute(final UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException {
    deleteExerciseService.execute(exerciseId);
    return ResponseEntity.noContent().build();
  }
}
