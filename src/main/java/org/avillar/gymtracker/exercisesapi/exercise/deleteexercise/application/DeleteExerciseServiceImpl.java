package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.application;

import jakarta.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleGroupExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.DeleteExerciseException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.ExerciseFacade;
import org.avillar.gymtracker.exercisesapi.common.facade.workout.WorkoutsFacade;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteExerciseServiceImpl implements DeleteExerciseService {

  private final ExerciseFacade exerciseFacade;
  private final MuscleGroupExerciseDao muscleGroupExerciseDao;
  private final AuthExercisesService authExercisesService;
  private final WorkoutsFacade workoutsFacade;

  @Transactional
  @Override
  public void execute(final UUID exerciseId)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {
    final Exercise exercise = exerciseFacade.getExerciseWithMuscleGroupExUses(exerciseId);

    authExercisesService.checkAccess(exercise, AuthOperations.DELETE);

    // TODO Check if the exercise is PRIVATE

    if (isExerciseInUse(exerciseId, exercise)) {
      throw new DeleteExerciseException("The exercise is used");
    }

    if (!exercise.getMuscleGroupExercises().isEmpty()) {
      muscleGroupExerciseDao.deleteAllById(
          exercise.getMuscleGroupExercises().stream()
              .map(MuscleGroupExercise::getId)
              .collect(Collectors.toSet()));
    }
    exerciseFacade.deleteExerciseById(exerciseId);
  }

  private boolean isExerciseInUse(final UUID exerciseId, final Exercise exercise) {
    try {
      return workoutsFacade.getExerciseUsesNumberForUser(exerciseId, exercise.getOwner()) > 0;
    } catch (WorkoutIllegalAccessException e) {
      return true;
    }
  }
}
