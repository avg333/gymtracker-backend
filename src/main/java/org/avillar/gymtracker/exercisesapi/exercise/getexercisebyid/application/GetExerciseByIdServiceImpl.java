package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application;

import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.mapper.GetExerciseByIdServiceMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetExerciseByIdServiceImpl implements GetExerciseByIdService {

  private final ExerciseDao exerciseDao;
  private final AuthExercisesService authExercisesService;
  private final GetExerciseByIdServiceMapper getExerciseByIdServiceMapper;

  @Override
  public GetExerciseByIdResponseApplication execute(final UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException {
    final Exercise exercise = getFullExercise(exerciseId);

    authExercisesService.checkAccess(exercise, AuthOperations.READ);

    return getExerciseByIdServiceMapper.map(exercise);
  }

  private Exercise getFullExercise(final UUID exerciseId) {
    return exerciseDao.getFullExerciseByIds(Set.of(exerciseId)).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseId));
  }
}
