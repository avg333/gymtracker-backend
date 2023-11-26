package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.ListOrderNotValidException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.UpdateSetGroupListOrderService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.mapper.UpdateSetGroupListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponse;
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
class UpdateSetGroupListOrderControllerImplTest {

  @InjectMocks private UpdateSetGroupListOrderControllerImpl controller;

  @Mock private UpdateSetGroupListOrderService service;
  @Mock private UpdateSetGroupListOrderControllerMapper mapper;

  @Test
  void shouldUpdateSetGroupListOrderWithReorderSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException, ListOrderNotValidException {
    final UUID setGroupId = UUID.randomUUID();
    final UpdateSetGroupListOrderRequest request =
        Instancio.create(UpdateSetGroupListOrderRequest.class);
    final List<SetGroup> response = Instancio.createList(SetGroup.class);
    final List<UpdateSetGroupListOrderResponse> responseDto =
        Instancio.createList(UpdateSetGroupListOrderResponse.class);

    when(service.execute(setGroupId, request.listOrder())).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(setGroupId, request)).isEqualTo(responseDto);
  }
}
