package org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.IncrementExerciseUsesService;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure.mapper.IncrementExerciseUsesControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure.model.IncrementExerciseUsesResponseInfrastructure;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IncrementExerciseUsesControllerImpl implements IncrementExerciseUsesController {

  private final IncrementExerciseUsesService incrementExerciseUsesService;
  private final IncrementExerciseUsesControllerMapper incrementExerciseUsesControllerMapper;

  @Override
  public IncrementExerciseUsesResponseInfrastructure execute(final UUID exerciseId) {
    return incrementExerciseUsesControllerMapper.map(
        incrementExerciseUsesService.execute(exerciseId));
  }
}
