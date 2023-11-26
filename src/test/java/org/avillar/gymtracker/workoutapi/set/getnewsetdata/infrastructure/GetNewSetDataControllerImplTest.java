package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.GetNewSetDataService;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.mapper.GetNewSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponse;
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
class GetNewSetDataControllerImplTest {

  @InjectMocks private GetNewSetDataControllerImpl controller;

  @Mock private GetNewSetDataService service;
  @Mock private GetNewSetDataControllerMapper mapper;

  @Test
  void shouldRetrieveNewSetDataSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final UUID setId = UUID.randomUUID();
    final Set response = Instancio.create(Set.class);
    final GetNewSetDataResponse responseDto = Instancio.create(GetNewSetDataResponse.class);

    when(service.execute(setId)).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(setId)).isEqualTo(responseDto);
  }
}
