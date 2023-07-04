package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.mapper.GetExercisesByFilterServiceMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetExercisesByFilterServiceImpl implements GetExercisesByFilterService {

  private final ExerciseDao exerciseDao;
  private final AuthExercisesService authExercisesService;
  private final GetExercisesByFilterServiceMapper getExercisesByFilterServiceMapper;

  @Override
  public List<GetExercisesByFilterResponseApplication> execute(
      final GetExercisesByFilterRequestApplication getExercisesByFilterRequestApplication) {
    final UUID loggedUserId = authExercisesService.getLoggedUserId();

    final List<Exercise> exercises =
        exerciseDao.getAllFullExercises(
            loggedUserId,
            AccessTypeEnum.PRIVATE,
            AccessTypeEnum.PUBLIC,
            getExercisesByFilterRequestApplication.getName(),
            getExercisesByFilterRequestApplication.getDescription(),
            getExercisesByFilterRequestApplication.getUnilateral());

    authExercisesService.checkAccess(exercises, AuthOperations.READ); // TODO Necesario esto?

    return getExercisesByFilterServiceMapper.map(exercises);
  }
}
