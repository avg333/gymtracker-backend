package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.CopySetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper.CopySetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequest;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequest.Source;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponse;
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
class CopySetGroupsControllerImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CopySetGroupsControllerImpl updateWorkoutSetGroupsController;

  @Mock private CopySetGroupsService copySetGroupsService;

  @Spy
  private final CopySetGroupsControllerMapper updateWorkoutSetGroupsControllerMapper =
      Mappers.getMapper(CopySetGroupsControllerMapper.class);

  @Test
  void copySetGroups() {
    final UUID workoutDestinationId = UUID.randomUUID();
    final CopySetGroupsRequest request = easyRandom.nextObject(CopySetGroupsRequest.class);
    final List<CopySetGroupsResponseApplication> expected =
        easyRandom.objects(CopySetGroupsResponseApplication.class, LIST_SIZE).toList();

    when(copySetGroupsService.execute(
            workoutDestinationId, request.getId(), request.getSource() == Source.WORKOUT))
        .thenReturn(expected);

    final List<CopySetGroupsResponse> result =
        updateWorkoutSetGroupsController.execute(workoutDestinationId, request);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
