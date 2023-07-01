package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.mapper.GetExercisesByIdsServiceMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetExercisesByIdsServiceImpl implements GetExercisesByIdsService {

  private final ExerciseDao exerciseDao;
  private final AuthExercisesService authExercisesService;
  private final GetExercisesByIdsServiceMapper getExercisesByIdsServiceMapper;

  @Override
  public List<GetExercisesByIdsResponseApplication> execute(Set<UUID> exerciseIds) {
    final List<Exercise> exercises = exerciseDao.getFullExerciseByIds(exerciseIds);

    authExercisesService.checkAccess(exercises, AuthOperations.READ);

    return getExercisesByIdsServiceMapper.map(exercises);
  }
}
