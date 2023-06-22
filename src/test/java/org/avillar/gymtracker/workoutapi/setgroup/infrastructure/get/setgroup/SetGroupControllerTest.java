package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.setgroup;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.GetSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.model.GetSetGroupResponseApplication.Workout;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.setgroup.mapper.GetSetGroupControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.setgroup.model.GetSetGroupResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SetGroupControllerTest {

  private SetGroupController setGroupController;

  @Mock private GetSetGroupService getSetGroupService;
  @Spy private GetSetGroupControllerMapperImpl getSetGroupControllerMapper;

  @BeforeEach
  void beforeEach() {
    setGroupController = new SetGroupController(getSetGroupService, getSetGroupControllerMapper);
  }

  @Test
  void getOk() {
    final UUID setGroupId = UUID.randomUUID();
    final int listOrder = 3;
    final String description = "Description example 54.";
    final UUID exerciseId = UUID.randomUUID();

    when(getSetGroupService.execute(setGroupId))
        .thenReturn(
            new GetSetGroupResponseApplication(
                setGroupId,
                listOrder,
                description,
                exerciseId,
                Collections.emptyList(),
                new Workout()));

    final GetSetGroupResponseInfrastructure getSetGroupResponseInfrastructure =
        setGroupController.get(setGroupId).getBody();
    Assertions.assertEquals(setGroupId, getSetGroupResponseInfrastructure.getId());
    Assertions.assertEquals(listOrder, getSetGroupResponseInfrastructure.getListOrder());
    Assertions.assertEquals(exerciseId, getSetGroupResponseInfrastructure.getExerciseId());
    Assertions.assertEquals(description, getSetGroupResponseInfrastructure.getDescription());
    Assertions.assertInstanceOf(
        GetSetGroupResponseInfrastructure.Workout.class,
        getSetGroupResponseInfrastructure.getWorkout());
  }
}
