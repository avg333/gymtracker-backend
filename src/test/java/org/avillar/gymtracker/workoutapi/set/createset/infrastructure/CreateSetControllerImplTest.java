package org.avillar.gymtracker.workoutapi.set.createset.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.createset.application.CreateSetService;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper.CreateSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponse;
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
class CreateSetControllerImplTest {

  @InjectMocks private CreateSetControllerImpl controller;

  @Mock private CreateSetService service;
  @Mock private CreateSetControllerMapper mapper;

  @Test
  void shouldCreateSetSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final UUID setGroupId = UUID.randomUUID();
    final CreateSetRequest requestDto = Instancio.create(CreateSetRequest.class);
    final Set request = Instancio.create(Set.class);
    final Set response = Instancio.create(Set.class);
    final CreateSetResponse responseDto = Instancio.create(CreateSetResponse.class);

    when(mapper.map(requestDto)).thenReturn(request);
    when(service.execute(setGroupId, request)).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(setGroupId, requestDto)).isEqualTo(responseDto);
  }
}
