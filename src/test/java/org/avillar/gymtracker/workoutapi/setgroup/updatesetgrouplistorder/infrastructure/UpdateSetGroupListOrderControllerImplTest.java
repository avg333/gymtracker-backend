package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.UpdateSetGroupListOrderService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.mapper.UpdateSetGroupListOrderControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponseInfrastructure;
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
class UpdateSetGroupListOrderControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetGroupListOrderControllerImpl updateSetGroupListOrderControllerImpl;

  @Mock private UpdateSetGroupListOrderService updateSetGroupListOrderService;

  @Spy private UpdateSetGroupListOrderControllerMapperImpl updateSetGroupListOrderControllerMapper;

  @Test
  void updateSetGroupExercise() {
    final UUID setGroupId = UUID.randomUUID();
    final int listOrder = 3;
    final UpdateSetGroupListOrderRequestInfrastructure
        updateSetGroupListOrderRequestInfrastructure =
            new UpdateSetGroupListOrderRequestInfrastructure();
    updateSetGroupListOrderRequestInfrastructure.setListOrder(listOrder);

    final List<UpdateSetGroupListOrderResponseApplication> expected =
        easyRandom.objects(UpdateSetGroupListOrderResponseApplication.class, 5).toList();

    when(updateSetGroupListOrderService.execute(setGroupId, listOrder)).thenReturn(expected);

    final ResponseEntity<List<UpdateSetGroupListOrderResponseInfrastructure>> response =
        updateSetGroupListOrderControllerImpl.execute(
            setGroupId, updateSetGroupListOrderRequestInfrastructure);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(expected.size(), response.getBody().size());
  }
}
