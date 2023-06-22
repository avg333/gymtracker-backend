package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.exercisesetgroups;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.GetExerciseSetGroupsService;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.model.GetExerciseSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.model.GetExerciseSetGroupsResponseApplication.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.exercisesetgroups.mapper.GetExerciseSetGroupsControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.exercisesetgroups.model.GetExerciseSetGroupsResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetSetExerciseSetGroupsControllerTest {

  private GetSetExerciseSetGroupsController getSetExerciseSetGroupsController;

  @Mock private GetExerciseSetGroupsService getExerciseSetGroupsService;
  @Spy private GetExerciseSetGroupsControllerMapperImpl getExerciseSetGroupsControllerMapper;

  @BeforeEach
  void beforeEach() {
    getSetExerciseSetGroupsController =
        new GetSetExerciseSetGroupsController(
            getExerciseSetGroupsService, getExerciseSetGroupsControllerMapper);
  }

  @Test
  void getOk() {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    final List<SetGroup> setGroups = List.of(new SetGroup(), new SetGroup());
    when(getExerciseSetGroupsService.execute(userId, exerciseId))
        .thenReturn(new GetExerciseSetGroupsResponseApplication(setGroups));

    final GetExerciseSetGroupsResponseInfrastructure getExerciseSetGroupsResponseInfrastructure =
        getSetExerciseSetGroupsController.get(userId, exerciseId).getBody();
    Assertions.assertEquals(
        setGroups.size(), getExerciseSetGroupsResponseInfrastructure.getSetGroups().size());
  }
}
