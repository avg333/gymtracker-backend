package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.UpdateSetGroupListOrderService;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.model.UpdateSetGroupListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.mapper.UpdateSetGroupListOrderControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.model.UpdateSetGroupListOrderRequestInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupListOrderControllerTest {

  private UpdateSetGroupListOrderController updateSetGroupListOrderController;

  @Mock private UpdateSetGroupListOrderService updateSetGroupListOrderService;

  @Spy private UpdateSetGroupListOrderControllerMapperImpl updateSetGroupListOrderControllerMapper;

  @BeforeEach
  void beforeEach() {
    updateSetGroupListOrderController =
        new UpdateSetGroupListOrderController(
            updateSetGroupListOrderService, updateSetGroupListOrderControllerMapper);
  }

  @Test
  void updateSetGroupExercise() {
    final UUID setGroupId = UUID.randomUUID();
    final int listOrder = 3;
    final UpdateSetGroupListOrderRequestInfrastructure
        updateSetGroupListOrderRequestInfrastructure =
            new UpdateSetGroupListOrderRequestInfrastructure();
    updateSetGroupListOrderRequestInfrastructure.setListOrder(listOrder);

    when(updateSetGroupListOrderService.execute(setGroupId, listOrder))
        .thenReturn(new UpdateSetGroupListOrderResponseApplication(Collections.emptySet()));

    Assertions.assertNotNull(
        updateSetGroupListOrderController
            .patch(setGroupId, updateSetGroupListOrderRequestInfrastructure)
            .getBody()
            .getSetGroups());
  }
}
