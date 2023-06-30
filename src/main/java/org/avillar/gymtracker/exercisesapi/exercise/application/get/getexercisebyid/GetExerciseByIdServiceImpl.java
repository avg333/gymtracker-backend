package org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid.mapper.GetExerciseServiceMapper;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid.model.GetExerciseByIdApplicationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetExerciseByIdServiceImpl implements GetExerciseByIdService {

  private final ExerciseDao exerciseDao;
  private final AuthExercisesService authExercisesService;
  private final GetExerciseServiceMapper getExerciseServiceMapper;

  @Override
  public GetExerciseByIdApplicationResponse execute(final UUID exerciseId) {
    final Exercise exercise =
        exerciseDao.getFullExerciseByIds(Set.of(exerciseId)).stream()
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(Exercise.class, exerciseId));

    authExercisesService.checkAccess(exercise, AuthOperations.READ);

    return getExerciseServiceMapper.map(exercise);
  }

  @Override
  public List<GetExerciseByIdApplicationResponse> execute(Set<UUID> exerciseIds) {
    final List<Exercise> exercises =
        exerciseDao.getFullExerciseByIds(exerciseIds);

    authExercisesService.checkAccess(exercises, AuthOperations.READ);

    return getExerciseServiceMapper.map(exercises);
  }
}
