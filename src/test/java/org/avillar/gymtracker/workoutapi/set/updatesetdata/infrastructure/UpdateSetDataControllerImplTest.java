package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponse;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper.UpdateSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequestDto;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseDto;
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
class UpdateSetDataControllerImplTest {

  @InjectMocks private UpdateSetDataControllerImpl controller;

  @Mock private UpdateSetDataService service;
  @Mock private UpdateSetDataControllerMapper mapper;

  @Test
  void shouldUpdateSetDataSuccessfully()
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final UUID setId = UUID.randomUUID();
    final UpdateSetDataRequestDto requestDto = Instancio.create(UpdateSetDataRequestDto.class);
    final UpdateSetDataRequest request = Instancio.create(UpdateSetDataRequest.class);
    final UpdateSetDataResponse response = Instancio.create(UpdateSetDataResponse.class);
    final UpdateSetDataResponseDto expected = Instancio.create(UpdateSetDataResponseDto.class);

    when(mapper.map(requestDto)).thenReturn(request);
    when(service.execute(setId, request)).thenReturn(response);
    when(mapper.map(response)).thenReturn(expected);

    assertThat(controller.execute(setId, requestDto)).isEqualTo(expected);
  }
}
