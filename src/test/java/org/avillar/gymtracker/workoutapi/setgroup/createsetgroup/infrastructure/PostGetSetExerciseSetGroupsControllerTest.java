package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.CreateSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication.Workout;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper.CreateSetGroupControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostGetSetExerciseSetGroupsControllerTest {

  private CreteSetGroupControllerImpl creteSetGroupControllerImpl;

  @Mock private CreateSetGroupService createSetGroupService;

  @Spy private CreateSetGroupControllerMapperImpl postSetGroupControllerMapper;

  @BeforeEach
  void beforeEach() {
    creteSetGroupControllerImpl =
        new CreteSetGroupControllerImpl(createSetGroupService, postSetGroupControllerMapper);
  }

  @Test
  void post() {
    final UUID workoutId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final String description = "Description example 54.";
    final CreateSetGroupRequestInfrastructure postWorkoutRequestInfrastructure =
        new CreateSetGroupRequestInfrastructure();
    postWorkoutRequestInfrastructure.setExerciseId(exerciseId);
    postWorkoutRequestInfrastructure.setDescription(description);

    final UUID setGroupId = UUID.randomUUID();
    final int listOrder = 0;
    when(createSetGroupService.execute(eq(workoutId), any(CreateSetGroupRequestApplication.class)))
        .thenReturn(
            new CreateSetGroupResponseApplication(
                setGroupId, listOrder, description, exerciseId, new Workout(workoutId)));

    final CreateSetGroupResponseInfrastructure createSetGroupResponseInfrastructure =
        creteSetGroupControllerImpl.execute(workoutId, postWorkoutRequestInfrastructure).getBody();
    Assertions.assertEquals(setGroupId, createSetGroupResponseInfrastructure.getId());
    Assertions.assertEquals(listOrder, createSetGroupResponseInfrastructure.getListOrder());
    Assertions.assertEquals(exerciseId, createSetGroupResponseInfrastructure.getExerciseId());
    Assertions.assertEquals(description, createSetGroupResponseInfrastructure.getDescription());
    Assertions.assertEquals(workoutId, createSetGroupResponseInfrastructure.getWorkout().getId());
  }
}
