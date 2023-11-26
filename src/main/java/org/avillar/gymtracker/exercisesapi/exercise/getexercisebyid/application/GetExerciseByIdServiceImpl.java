package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.ExerciseFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetExerciseByIdServiceImpl implements GetExerciseByIdService {

  private final ExerciseFacade exerciseFacade;
  private final AuthExercisesService authExercisesService;

  @Override
  public Exercise execute(final UUID exerciseId)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {
    final Exercise exercise = exerciseFacade.getFullExerciseById(exerciseId);

    authExercisesService.checkAccess(exercise, AuthOperations.READ);

    return exercise;
  }
}
