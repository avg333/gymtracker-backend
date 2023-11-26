package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.ExerciseFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetExercisesByIdsServiceImpl implements GetExercisesByIdsService {

  private final ExerciseFacade exerciseFacade;
  private final AuthExercisesService authExercisesService;

  @Override
  public List<Exercise> execute(final Collection<UUID> exerciseIds) throws IllegalAccessException {
    final List<Exercise> exercises = exerciseFacade.getExercisesByIds(exerciseIds);

    authExercisesService.checkAccess(exercises, AuthOperations.READ);

    return exercises;
  }
}
