package org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseUsesDao;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.model.IncrementExerciseUsesResponseApplication;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException.AccessError;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IncrementExerciseUsesServiceImpl implements IncrementExerciseUsesService {

  private final AuthExercisesService authExercisesService;
  private final ExerciseDao exerciseDao;
  private final ExerciseUsesDao exerciseUsesDao;

  @Override
  public IncrementExerciseUsesResponseApplication execute(final UUID exerciseId) {
    final UUID userId = authExercisesService.getLoggedUserId();

    final Exercise exercise =
        exerciseDao
            .findById(exerciseId)
            .orElseThrow(() -> new ExerciseNotFoundException(exerciseId, AccessError.NOT_FOUND));

    authExercisesService.checkAccess(exercise, AuthOperations.READ);

    final ExerciseUses exerciseUses =
        exerciseUsesDao.getExerciseUsesByExerciseIdAndUserId(exerciseId, userId).stream()
            .findAny()
            .orElse(new ExerciseUses());

    exerciseUses.setExercise(exercise);
    exerciseUses.setUserId(userId);
    exerciseUses.setUses(getIncrementedUses(exerciseUses.getUses()));

    exerciseUsesDao.save(exerciseUses);

    return IncrementExerciseUsesResponseApplication.builder().uses(exerciseUses.getUses()).build();
  }

  private static int getIncrementedUses(final Integer uses) {
    if (uses == null) {
      return 1;
    } else {
      return uses + 1;
    }
  }
}
