package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure;

import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.UpdateSetGroupListOrderService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.mapper.UpdateSetGroupListOrderControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponseInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponseInfrastructure.SetGroup;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupListOrderControllerTest {

  private EasyRandom easyRandom = new EasyRandom();

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

    final UpdateSetGroupListOrderResponseApplication updateSetGroupListOrderResponseApplication =
        easyRandom.nextObject(UpdateSetGroupListOrderResponseApplication.class);

    when(updateSetGroupListOrderService.execute(setGroupId, listOrder))
        .thenReturn(updateSetGroupListOrderResponseApplication);

    final ResponseEntity<UpdateSetGroupListOrderResponseInfrastructure>
        updateSetGroupListOrderResponseInfrastructure =
            updateSetGroupListOrderController.patch(
                setGroupId, updateSetGroupListOrderRequestInfrastructure);

    Assertions.assertNotNull(updateSetGroupListOrderResponseInfrastructure.getBody());

    final Set<UpdateSetGroupListOrderResponseApplication.SetGroup> setGroupsExpected =
        updateSetGroupListOrderResponseApplication.getSetGroups();
    final Set<SetGroup> setGroupsResult =
        updateSetGroupListOrderResponseInfrastructure.getBody().getSetGroups();

    Assertions.assertEquals(
        setGroupsExpected.stream().toList().get(0).getId(),
        setGroupsResult.stream().toList().get(0).getId());
    Assertions.assertEquals(
        setGroupsExpected.stream().toList().get(0).getListOrder(),
        setGroupsResult.stream().toList().get(0).getListOrder());
    Assertions.assertEquals(
        setGroupsExpected.stream().toList().get(0).getDescription(),
        setGroupsResult.stream().toList().get(0).getDescription());
    Assertions.assertEquals(
        setGroupsExpected.stream().toList().get(0).getExerciseId(),
        setGroupsResult.stream().toList().get(0).getExerciseId());
  }
}
