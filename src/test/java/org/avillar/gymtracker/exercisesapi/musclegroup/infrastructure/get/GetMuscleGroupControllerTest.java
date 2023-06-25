package org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.GetMuscleGroupService;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model.GetMuscleGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.mapper.GetMuscleGroupControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.model.GetMuscleGroupsInfrastructureResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetMuscleGroupControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();
  private GetMuscleGroupController getMuscleGroupController;
  @Mock private GetMuscleGroupService getMuscleGroupService;
  @Spy private GetMuscleGroupControllerMapperImpl getMuscleGroupControllerMapper;

  @BeforeEach
  void beforeEach() {
    getMuscleGroupController =
        new GetMuscleGroupController(getMuscleGroupService, getMuscleGroupControllerMapper);
  }

  @Test
  void get() {
    final UUID muscleSupGroupId = UUID.randomUUID();
    final List<GetMuscleGroupsApplicationResponse> getMuscleGroupsApplicationResponses =
        easyRandom.objects(GetMuscleGroupsApplicationResponse.class, 2).toList();

    when(getMuscleGroupService.execute(muscleSupGroupId))
        .thenReturn(getMuscleGroupsApplicationResponses);

    final List<GetMuscleGroupsInfrastructureResponse> getMuscleGroupsInfrastructureResponses =
        getMuscleGroupController.get(muscleSupGroupId).getBody();
    assertEquals(
        getMuscleGroupsApplicationResponses.size(), getMuscleGroupsInfrastructureResponses.size());
    assertEquals(
        getMuscleGroupsApplicationResponses.get(0).getId(),
        getMuscleGroupsInfrastructureResponses.get(0).getId());
    assertEquals(
        getMuscleGroupsApplicationResponses.get(0).getName(),
        getMuscleGroupsInfrastructureResponses.get(0).getName());
    assertEquals(
        getMuscleGroupsApplicationResponses.get(0).getDescription(),
        getMuscleGroupsInfrastructureResponses.get(0).getDescription());
  }
}
