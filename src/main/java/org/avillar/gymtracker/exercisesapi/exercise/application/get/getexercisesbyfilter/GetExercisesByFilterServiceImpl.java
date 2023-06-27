package org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.mapper.GetExercisesByFilterServiceMapper;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.model.GetExercisesByFilterApplicationRequest;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.model.GetExercisesByFilterApplicationResponse;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.domain.ExerciseDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetExercisesByFilterServiceImpl implements GetExercisesByFilterService {

  private final ExerciseDao exerciseDao;
  private final AuthExercisesService authExercisesService;
  private final GetExercisesByFilterServiceMapper getExercisesByFilterServiceMapper;

  @Override
  public List<GetExercisesByFilterApplicationResponse> execute(
      GetExercisesByFilterApplicationRequest getExercisesByFilterApplicationRequest) {
    final List<Exercise> exercises = exerciseDao.getAllFullExercises(); // TODO Usar filtro

    authExercisesService.checkAccess(exercises, AuthOperations.READ);

    return getExercisesByFilterServiceMapper.map(exercises);
  }
}
