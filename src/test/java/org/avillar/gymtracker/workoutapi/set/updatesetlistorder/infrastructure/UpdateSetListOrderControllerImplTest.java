package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper.UpdateSetListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponse;
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
class UpdateSetListOrderControllerImplTest {

  @InjectMocks private UpdateSetListOrderControllerImpl controller;

  @Mock private UpdateSetListOrderService service;
  @Mock private UpdateSetListOrderControllerMapper mapper;

  @Test
  void shouldUpdateSetListOrderSuccessfully()
      throws SetNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    final UUID setId = UUID.randomUUID();
    final UpdateSetListOrderRequest requestDto = Instancio.create(UpdateSetListOrderRequest.class);
    final List<Set> response = Instancio.createList(Set.class);
    final List<UpdateSetListOrderResponse> responseDto =
        Instancio.createList(UpdateSetListOrderResponse.class);

    when(service.execute(setId, requestDto.listOrder())).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(setId, requestDto)).isEqualTo(responseDto);
  }
}
