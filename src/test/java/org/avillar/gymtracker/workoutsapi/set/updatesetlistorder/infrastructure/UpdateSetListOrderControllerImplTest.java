package org.avillar.gymtracker.workoutsapi.set.updatesetlistorder.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.UpdateSetListOrderControllerImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper.UpdateSetListOrderControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateSetListOrderControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetListOrderControllerImpl updateSetListOrderControllerImpl;

  @Mock private UpdateSetListOrderService updateSetListOrderService;
  @Spy private UpdateSetListOrderControllerMapperImpl updateSetListOrderControllerMapper;

  @Test
  void updateSetData() {
    final UUID setId = UUID.randomUUID();
    final int listOrder = 3;
    final UpdateSetListOrderRequestInfrastructure updateSetListOrderRequestInfrastructure =
        new UpdateSetListOrderRequestInfrastructure();
    updateSetListOrderRequestInfrastructure.setListOrder(listOrder);

    final List<UpdateSetListOrderResponseApplication> expected =
        easyRandom.objects(UpdateSetListOrderResponseApplication.class, 5).toList();

    when(updateSetListOrderService.execute(setId, listOrder)).thenReturn(expected);

    final ResponseEntity<List<UpdateSetListOrderResponseInfrastructure>> result =
        updateSetListOrderControllerImpl.execute(setId, updateSetListOrderRequestInfrastructure);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().size());
    assertEquals(expected.get(0).getId(), result.getBody().get(0).getId());
    assertEquals(expected.get(0).getListOrder(), result.getBody().get(0).getListOrder());
    assertEquals(expected.get(0).getRir(), result.getBody().get(0).getRir());
    assertEquals(expected.get(0).getWeight(), result.getBody().get(0).getWeight());
    assertEquals(expected.get(0).getReps(), result.getBody().get(0).getReps());
    assertEquals(expected.get(0).getDescription(), result.getBody().get(0).getDescription());
    assertEquals(
        expected.get(0).getSetGroup().getId(), result.getBody().get(0).getSetGroup().getId());
  }
}
