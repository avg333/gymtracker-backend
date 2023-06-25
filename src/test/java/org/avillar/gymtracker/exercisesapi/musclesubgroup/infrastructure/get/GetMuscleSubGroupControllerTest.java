package org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.GetMuscleSubGroupService;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model.GetMuscleSubGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.mapper.GetMuscleSubGroupControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.model.GetMuscleSubGroupsInfrastructureResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetMuscleSubGroupControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();
  private GetMuscleSubGroupController getMuscleSubGroupController;
  @Mock private GetMuscleSubGroupService getMuscleSubGroupService;
  @Spy private GetMuscleSubGroupControllerMapperImpl getMuscleSubGroupControllerMapper;

  @BeforeEach
  void beforeEach() {
    getMuscleSubGroupController =
        new GetMuscleSubGroupController(
            getMuscleSubGroupService, getMuscleSubGroupControllerMapper);
  }

  @Test
  void get() {
    final UUID muscleGroupId = UUID.randomUUID();
    final List<GetMuscleSubGroupsApplicationResponse> getMuscleSubGroupsApplicationResponses =
        easyRandom.objects(GetMuscleSubGroupsApplicationResponse.class, 2).toList();

    when(getMuscleSubGroupService.execute(muscleGroupId))
        .thenReturn(getMuscleSubGroupsApplicationResponses);

    final List<GetMuscleSubGroupsInfrastructureResponse> getMuscleSubGroupsInfrastructureResponses =
        getMuscleSubGroupController.get(muscleGroupId).getBody();
    assertEquals(
        getMuscleSubGroupsApplicationResponses.size(),
        getMuscleSubGroupsInfrastructureResponses.size());
    assertEquals(
        getMuscleSubGroupsApplicationResponses.get(0).getId(),
        getMuscleSubGroupsInfrastructureResponses.get(0).getId());
    assertEquals(
        getMuscleSubGroupsApplicationResponses.get(0).getName(),
        getMuscleSubGroupsInfrastructureResponses.get(0).getName());
    assertEquals(
        getMuscleSubGroupsApplicationResponses.get(0).getDescription(),
        getMuscleSubGroupsInfrastructureResponses.get(0).getDescription());
  }
}
