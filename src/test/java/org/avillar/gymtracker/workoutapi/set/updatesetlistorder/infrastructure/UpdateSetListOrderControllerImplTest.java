package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper.UpdateSetListOrderControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
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

    easyRandom.objects(UpdateSetListOrderResponseApplication.class, 5);

    when(updateSetListOrderService.execute(setId, listOrder))
        .thenReturn(easyRandom.objects(UpdateSetListOrderResponseApplication.class, 5).toList());

    final ResponseEntity<List<UpdateSetListOrderResponseInfrastructure>> result =
        updateSetListOrderControllerImpl.execute(setId, updateSetListOrderRequestInfrastructure);
    Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    Assertions.assertNotNull(result.getBody());
    Assertions.assertEquals(5, result.getBody().size());
  }
}
