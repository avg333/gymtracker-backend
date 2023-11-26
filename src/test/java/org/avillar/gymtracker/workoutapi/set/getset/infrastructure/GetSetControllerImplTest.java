package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.getset.application.GetSetService;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper.GetSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponse;
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
class GetSetControllerImplTest {

  @InjectMocks private GetSetControllerImpl controller;

  @Mock private GetSetService service;
  @Mock private GetSetControllerMapper mapper;

  @Test
  void shouldGetSetSuccessfully() throws SetNotFoundException, WorkoutIllegalAccessException {
    final UUID setId = UUID.randomUUID();
    final Set response = Instancio.create(Set.class);
    final GetSetResponse responseDto = Instancio.create(GetSetResponse.class);

    when(service.execute(setId)).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(setId)).isEqualTo(responseDto);
  }
}
