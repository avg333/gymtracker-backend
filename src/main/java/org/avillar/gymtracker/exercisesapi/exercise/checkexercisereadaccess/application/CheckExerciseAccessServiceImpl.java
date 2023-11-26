package org.avillar.gymtracker.exercisesapi.exercise.checkexercisereadaccess.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.ExerciseFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckExerciseAccessServiceImpl implements CheckExerciseAccessService {

  private final ExerciseFacade exerciseFacade;
  private final AuthExercisesService authExercisesService;

  @Override
  public void execute(final UUID exerciseId, final AuthOperations authOperations)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {

    // TODO Use findById instead of getExerciseById
    authExercisesService.checkAccess(exerciseFacade.getExerciseById(exerciseId), authOperations);
  }
}
