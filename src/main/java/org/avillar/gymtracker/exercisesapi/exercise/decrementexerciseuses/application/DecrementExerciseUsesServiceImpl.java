package org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseUsesDao;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.model.DecrementExerciseUsesResponseApplication;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException.AccessError;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DecrementExerciseUsesServiceImpl implements DecrementExerciseUsesService {

  private final AuthExercisesService authExercisesService;
  private final ExerciseDao exerciseDao;
  private final ExerciseUsesDao exerciseUsesDao;

  @Override
  public DecrementExerciseUsesResponseApplication execute(final UUID exerciseId) {
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
    exerciseUses.setUses(getDecrementedUses(exerciseUses.getUses()));

    exerciseUsesDao.save(exerciseUses);

    return DecrementExerciseUsesResponseApplication.builder().uses(exerciseUses.getUses()).build();
  }

  private int getDecrementedUses(final Integer uses) {

    if (uses == null) {
      return 0;
    } else {
      return Math.max(0, uses - 1);
    }
  }
}
