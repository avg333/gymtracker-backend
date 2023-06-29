package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper.UpdateSetListOrderControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequestInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetListOrderControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

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
        .thenReturn(easyRandom.nextObject(UpdateSetListOrderResponseApplication.class));

    Assertions.assertNotNull(
        updateSetListOrderController
            .patch(setId, updateSetListOrderRequestInfrastructure)
            .getBody()
            .getSets());
  }
}
