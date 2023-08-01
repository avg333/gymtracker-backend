package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application;

import jakarta.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupExerciseDao;
import org.avillar.gymtracker.exercisesapi.exception.application.DeleteExerciseException;
import org.avillar.gymtracker.exercisesapi.workout.application.facade.WorkoutRepositoryClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteExerciseServiceImpl implements DeleteExerciseService {

  private final ExerciseDao exerciseDao;
  private final MuscleGroupExerciseDao muscleGroupExerciseDao;
  private final AuthExercisesService authExercisesService;
  private final WorkoutRepositoryClient workoutRepositoryClient;

  @Transactional
  @Override
  public void execute(final UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException {
    final Exercise exercise = getExerciseWithMuscleGroupEx(exerciseId);

    authExercisesService.checkAccess(exercise, AuthOperations.DELETE);

    if (workoutRepositoryClient.getExerciseUsesNumberForUser(exerciseId, exercise.getOwner()) > 0) {
      throw new DeleteExerciseException("The exercise is used");
    }

    if (!exercise.getMuscleGroupExercises().isEmpty()) {
      muscleGroupExerciseDao.deleteAllById(
          exercise.getMuscleGroupExercises().stream()
              .map(BaseEntity::getId)
              .collect(Collectors.toSet()));
    }
    exerciseDao.deleteById(exerciseId);
  }

  private Exercise getExerciseWithMuscleGroupEx(final UUID exerciseId) {
    return exerciseDao.getExerciseByIdWithMuscleGroupEx(exerciseId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseId));
  }
}
