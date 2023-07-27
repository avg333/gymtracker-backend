package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.mapper.GetExercisesByFilterServiceMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

@ExtendWith(MockitoExtension.class)
class GetExercisesByFilterServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExercisesByFilterServiceImpl getExercisesByFilterService;

  @Mock private ExerciseDao exerciseDao;
  @Mock private AuthExercisesService authExercisesService;

  @Spy
  private GetExercisesByFilterServiceMapper getExercisesByFilterServiceMapper =
      Mappers.getMapper(GetExercisesByFilterServiceMapper.class);

  @Test
  void getOk() {
    final GetExercisesByFilterRequestApplication request =
        easyRandom.nextObject(GetExercisesByFilterRequestApplication.class);
    final List<Exercise> expected = easyRandom.objects(Exercise.class, 5).toList();
    final UUID userId = UUID.randomUUID();
    expected.stream()
        .filter(exercise -> exercise.getAccessType() == AccessTypeEnum.PRIVATE)
        .forEach(exercise -> exercise.setOwner(userId));

    when(authExercisesService.getLoggedUserId()).thenReturn(userId);
    when(exerciseDao.getAllFullExercises(
            userId,
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
                : request.getMuscleGroupIds()))
        .thenReturn(expected);
    doNothing().when(authExercisesService).checkAccess(expected, AuthOperations.READ);

    final List<GetExercisesByFilterResponseApplication> result =
        getExercisesByFilterService.execute(request);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
