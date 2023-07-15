package org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckExerciseReadAccessServiceImpl implements CheckExerciseReadAccessService {

  private final ExerciseDao exerciseDao;
  private final AuthExercisesService authExercisesService;

  @Override
  public void execute(final UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException {

    final Exercise exercise =
        exerciseDao
            .findById(exerciseId)
            .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseId));

    authExercisesService.checkAccess(exercise, AuthOperations.READ);
  }
}
