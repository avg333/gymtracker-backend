package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
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
    final UpdateSetListOrderRequestInfrastructure updateSetListOrderRequestInfrastructure =
        easyRandom.nextObject(UpdateSetListOrderRequestInfrastructure.class);
    final List<UpdateSetListOrderResponseApplication> expected =
        easyRandom.objects(UpdateSetListOrderResponseApplication.class, 5).toList();

    when(updateSetListOrderService.execute(
            setId, updateSetListOrderRequestInfrastructure.getListOrder()))
        .thenReturn(expected);

    final ResponseEntity<List<UpdateSetListOrderResponseInfrastructure>> result =
        updateSetListOrderControllerImpl.execute(setId, updateSetListOrderRequestInfrastructure);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().size());
    for (int i = 0; i < expected.size(); i++) {
      final var setExpected = expected.get(i);
      final var setResult = result.getBody().get(i);
      assertEquals(setExpected.getId(), setResult.getId());
      assertEquals(setExpected.getListOrder(), setResult.getListOrder());
      assertEquals(setExpected.getRir(), setResult.getRir());
      assertEquals(setExpected.getWeight(), setResult.getWeight());
      assertEquals(setExpected.getReps(), setResult.getReps());
      assertEquals(setExpected.getDescription(), setResult.getDescription());
      assertEquals(setExpected.getCompletedAt(), setResult.getCompletedAt());
      assertEquals(setExpected.getSetGroup().getId(), setResult.getSetGroup().getId());
    }
  }
}
