package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupExerciseDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteExerciseServiceImpl implements DeleteExerciseService {

  private final ExerciseDao exerciseDao;
  private final MuscleGroupExerciseDao muscleGroupExerciseDao;
  private final AuthExercisesService authExercisesService;

  @Transactional
  @Override
  public void execute(final UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException {
    final Exercise exercise = getExerciseWithMuscleGroupEx(exerciseId);

    authExercisesService.checkAccess(exercise, AuthOperations.DELETE);

    // TODO Check if it is used in some workout

    if (!exercise.getMuscleGroupExercises().isEmpty()) {
      muscleGroupExerciseDao.deleteAllById(
          exercise.getMuscleGroupExercises().stream().map(BaseEntity::getId).toList());
    }
    exerciseDao.deleteById(exerciseId);
  }

  private Exercise getExerciseWithMuscleGroupEx(final UUID exerciseId) {
    return exerciseDao.getExerciseByIdWithMuscleGroupEx(exerciseId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseId));
  }
}
