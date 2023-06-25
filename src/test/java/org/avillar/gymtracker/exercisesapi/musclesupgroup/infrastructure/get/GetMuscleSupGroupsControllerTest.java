package org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.GetMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model.GetMuscleSupGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.mapper.GetMuscleSupGroupControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.model.GetMuscleSupGroupsInfrastructureResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetMuscleSupGroupsControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();
  private GetMuscleSupGroupsController getMuscleSupGroupsController;
  @Mock private GetMuscleSupGroupService getMuscleSupGroupService;
  @Spy private GetMuscleSupGroupControllerMapperImpl getMuscleSupGroupControllerMapper;

  @BeforeEach
  void beforeEach() {
    getMuscleSupGroupsController =
        new GetMuscleSupGroupsController(
            getMuscleSupGroupService, getMuscleSupGroupControllerMapper);
  }

  @Test
  void get() {
    final List<GetMuscleSupGroupsApplicationResponse> getMuscleSupGroupsApplicationResponses =
        easyRandom.objects(GetMuscleSupGroupsApplicationResponse.class, 2).toList();

    when(getMuscleSupGroupService.execute()).thenReturn(getMuscleSupGroupsApplicationResponses);

    final List<GetMuscleSupGroupsInfrastructureResponse> getMuscleSupGroupsInfrastructureResponses =
        getMuscleSupGroupsController.get().getBody();
    assertEquals(
        getMuscleSupGroupsApplicationResponses.size(),
        getMuscleSupGroupsInfrastructureResponses.size());
    assertEquals(
        getMuscleSupGroupsApplicationResponses.get(0).getId(),
        getMuscleSupGroupsInfrastructureResponses.get(0).getId());
    assertEquals(
        getMuscleSupGroupsApplicationResponses.get(0).getName(),
        getMuscleSupGroupsInfrastructureResponses.get(0).getName());
    assertEquals(
        getMuscleSupGroupsApplicationResponses.get(0).getDescription(),
        getMuscleSupGroupsInfrastructureResponses.get(0).getDescription());
    assertEquals(
        getMuscleSupGroupsApplicationResponses.get(0).getMuscleGroups().size(),
        getMuscleSupGroupsInfrastructureResponses.get(0).getMuscleGroups().size());
  }
}
