package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.GetSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication.Workout;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper.GetSetGroupControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SetGroupControllerImplTest {

  private SetGroupControllerImpl setGroupControllerImpl;

  @Mock private GetSetGroupService getSetGroupService;
  @Spy private GetSetGroupControllerMapperImpl getSetGroupControllerMapper;

  @BeforeEach
  void beforeEach() {
    setGroupControllerImpl = new SetGroupControllerImpl(getSetGroupService, getSetGroupControllerMapper);
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
        setGroupControllerImpl.get(setGroupId).getBody();
    Assertions.assertEquals(setGroupId, getSetGroupResponseInfrastructure.getId());
    Assertions.assertEquals(listOrder, getSetGroupResponseInfrastructure.getListOrder());
    Assertions.assertEquals(exerciseId, getSetGroupResponseInfrastructure.getExerciseId());
    Assertions.assertEquals(description, getSetGroupResponseInfrastructure.getDescription());
    Assertions.assertInstanceOf(
        GetSetGroupResponseInfrastructure.Workout.class,
        getSetGroupResponseInfrastructure.getWorkout());
  }
}
