package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application;

import java.util.Collections;
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
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class GetExercisesByFilterServiceImpl implements GetExercisesByFilterService {

  private final ExerciseDao exerciseDao;
  private final AuthExercisesService authExercisesService;
  private final GetExercisesByFilterServiceMapper getExercisesByFilterServiceMapper;

  @Override
  public List<GetExercisesByFilterResponseApplication> execute(
      final GetExercisesByFilterRequestApplication request) {

    final UUID loggedUserId = authExercisesService.getLoggedUserId();

    final List<Exercise> exercises =
        exerciseDao.getAllFullExercises(
            loggedUserId,
            AccessTypeEnum.PRIVATE,
            AccessTypeEnum.PUBLIC,
            request.getName(),
            request.getDescription(),
            request.getUnilateral(),
            CollectionUtils.isEmpty(request.getLoadTypeIds()),
            CollectionUtils.isEmpty(request.getLoadTypeIds())
                ? Collections.emptyList()
                : request.getLoadTypeIds(),
            CollectionUtils.isEmpty(request.getMuscleSubGroupIds()),
            CollectionUtils.isEmpty(request.getMuscleSubGroupIds())
                ? Collections.emptyList()
                : request.getMuscleSubGroupIds(),
            CollectionUtils.isEmpty(request.getMuscleSupGroupIds()),
            CollectionUtils.isEmpty(request.getMuscleSupGroupIds())
                ? Collections.emptyList()
                : request.getMuscleSupGroupIds(),
            CollectionUtils.isEmpty(request.getMuscleGroupIds()),
            CollectionUtils.isEmpty(request.getMuscleGroupIds())
                ? Collections.emptyList()
                : request.getMuscleGroupIds());

    authExercisesService.checkAccess(exercises, AuthOperations.READ);

    return getExercisesByFilterServiceMapper.map(exercises);
  }
}
