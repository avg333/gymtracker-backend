package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.CreateExerciseService;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.mapper.CreteExerciseControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateExerciseControllerImpl implements CreateExerciseController {

  private final CreateExerciseService createExerciseService;
  private final CreteExerciseControllerMapper creteExerciseControllerMapper;

  @Override
  public ResponseEntity<CreateExerciseResponse> execute(
      final UUID userId, final CreateExerciseRequest createExerciseRequest)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        creteExerciseControllerMapper.map(
            createExerciseService.execute(
                userId, creteExerciseControllerMapper.map(createExerciseRequest))));
  }
}
