package org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.update.listorder.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.application.update.listorder.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.mapper.UpdateSetListOrderControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.model.UpdateSetListOrderRequestInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetListOrderControllerTest {

  private UpdateSetListOrderController updateSetListOrderController;

  @Mock private UpdateSetListOrderService updateSetListOrderService;
  @Spy private UpdateSetListOrderControllerMapperImpl updateSetListOrderControllerMapper;

  @BeforeEach
  void beforeEach() {
    updateSetListOrderController =
        new UpdateSetListOrderController(
            updateSetListOrderService, updateSetListOrderControllerMapper);
  }

  @Test
  void updateSetData() {
    final UUID setId = UUID.randomUUID();
    final int listOrder = 3;
    final UpdateSetListOrderRequestInfrastructure updateSetListOrderRequestInfrastructure =
        new UpdateSetListOrderRequestInfrastructure();
    updateSetListOrderRequestInfrastructure.setListOrder(listOrder);

    when(updateSetListOrderService.execute(setId, listOrder))
        .thenReturn(new UpdateSetListOrderResponseApplication(Collections.emptySet()));

    Assertions.assertNotNull(
        updateSetListOrderController
            .patch(setId, updateSetListOrderRequestInfrastructure)
            .getBody()
            .getSets());
  }
}
