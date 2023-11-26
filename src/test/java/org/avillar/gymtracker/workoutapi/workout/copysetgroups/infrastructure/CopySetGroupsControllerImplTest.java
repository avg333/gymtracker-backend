package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsServiceImpl.CopySetGroupsRequest;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper.CopySetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestDto;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CopySetGroupsControllerImplTest {

  @InjectMocks private CopySetGroupsControllerImpl controller;

  @Mock private CopySetGroupsService service;
  @Mock private CopySetGroupsControllerMapper mapper;

  @Test
  void shouldCopySetGroupsSuccessfully()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final UUID workoutDestinationId = UUID.randomUUID();
    final CopySetGroupsRequestDto requestDto = Instancio.create(CopySetGroupsRequestDto.class);
    final CopySetGroupsRequest request = Instancio.create(CopySetGroupsRequest.class);
    final List<SetGroup> response = Instancio.createList(SetGroup.class);
    final List<CopySetGroupsResponseDto> expected =
        Instancio.createList(CopySetGroupsResponseDto.class);

    when(mapper.map(requestDto)).thenReturn(request);
    when(service.execute(workoutDestinationId, request)).thenReturn(response);
    when(mapper.map(response)).thenReturn(expected);

    assertThat(controller.execute(workoutDestinationId, requestDto)).isEqualTo(expected);
  }
}
