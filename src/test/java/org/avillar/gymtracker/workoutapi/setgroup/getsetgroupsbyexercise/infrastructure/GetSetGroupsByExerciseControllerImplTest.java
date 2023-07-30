package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.GetSetGroupsByExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetSetGroupsByExerciseResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.mapper.GetSetGroupsByExerciseControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetSetGroupsByExerciseControllerImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetSetGroupsByExerciseControllerImpl getSetGroupsByExerciseControllerImpl;

  @Mock private GetSetGroupsByExerciseService getSetGroupsByExerciseService;

  @Spy
  private final GetSetGroupsByExerciseControllerMapper getExerciseSetGroupsControllerMapper =
      Mappers.getMapper(GetSetGroupsByExerciseControllerMapper.class);

  @Test
  void getOk() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<GetSetGroupsByExerciseResponseApplication> expected =
        easyRandom.objects(GetSetGroupsByExerciseResponseApplication.class, LIST_SIZE).toList();
    expected.forEach(sg -> sg.setExerciseId(exerciseId));

    when(getSetGroupsByExerciseService.execute(userId, exerciseId)).thenReturn(expected);

    final List<GetSetGroupsByExerciseResponse> result =
        getSetGroupsByExerciseControllerImpl.execute(userId, exerciseId);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
