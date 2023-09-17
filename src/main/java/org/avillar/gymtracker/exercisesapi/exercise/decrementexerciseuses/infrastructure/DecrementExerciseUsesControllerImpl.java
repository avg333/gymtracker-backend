package org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.DecrementExerciseUsesService;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.infrastructure.mapper.DecrementExerciseUsesControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.infrastructure.model.DecrementExerciseUsesResponseInfrastructure;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DecrementExerciseUsesControllerImpl implements DecrementExerciseUsesController {

  private final DecrementExerciseUsesService decrementExerciseUsesService;
  private final DecrementExerciseUsesControllerMapper decrementExerciseUsesControllerMapper;

  @Override
  public DecrementExerciseUsesResponseInfrastructure execute(final UUID exerciseId) {
    return decrementExerciseUsesControllerMapper.map(
        decrementExerciseUsesService.execute(exerciseId));
  }
}
