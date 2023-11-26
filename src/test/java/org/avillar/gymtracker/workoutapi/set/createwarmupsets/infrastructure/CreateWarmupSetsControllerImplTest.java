package org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.application.CreateWarmupSetsService;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.mapper.CreateWarmupSetsControllerMapper;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model.CreateWarmupSetsRequest;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;
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
class CreateWarmupSetsControllerImplTest {

  @InjectMocks private CreateWarmupSetsControllerImpl controller;

  @Mock private CreateWarmupSetsService service;
  @Mock private CreateWarmupSetsControllerMapper mapper;

  @Test
  void shouldExecuteCreateWarmupSetsSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final UUID setGroupId = UUID.randomUUID();
    final CreateWarmupSetsRequest requestDto = Instancio.create(CreateWarmupSetsRequest.class);
    final List<Set> response = Instancio.createList(Set.class);
    final List<CreateWarmupSetsResponse> expected =
        Instancio.createList(CreateWarmupSetsResponse.class);

    when(service.execute(setGroupId, null, null)).thenReturn(response);
    when(mapper.map(response)).thenReturn(expected);

    assertThat(controller.execute(setGroupId, requestDto)).isEqualTo(expected);
  }
}
