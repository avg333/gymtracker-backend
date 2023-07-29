package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.GetExercisesByIdsService;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.mapper.GetExercisesByIdsControllerMapper;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetExercisesByIdsControllerImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetExercisesByIdsControllerImpl getExercisesByIdsController;

  @Mock private GetExercisesByIdsService getExercisesByIdsService;

  @Spy
  private final GetExercisesByIdsControllerMapper getExercisesByIdsControllerMapper =
      Mappers.getMapper(GetExercisesByIdsControllerMapper.class);

  @Test
  void get() {
    final List<GetExercisesByIdsResponseApplication> expected =
        easyRandom.objects(GetExercisesByIdsResponseApplication.class, LIST_SIZE).toList();
    final Set<UUID> request =
        expected.stream()
            .map(GetExercisesByIdsResponseApplication::getId)
            .collect(Collectors.toSet());

    when(getExercisesByIdsService.execute(request)).thenReturn(expected);

    final List<GetExercisesByIdsResponseInfrastructure> result =
        getExercisesByIdsController.execute(request);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
