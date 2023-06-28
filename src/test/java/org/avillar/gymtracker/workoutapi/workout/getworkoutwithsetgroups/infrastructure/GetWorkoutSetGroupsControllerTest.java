package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.GetWorkoutSetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.model.GetWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.mapper.GetWorkoutSetGroupsControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutSetGroupsControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetWorkoutSetGroupsController getWorkoutSetGroupsController;

  @Mock private GetWorkoutSetGroupsService getWorkoutSetGroupsService;
  @Spy private GetWorkoutSetGroupsControllerMapperImpl getWorkoutSetGroupsControllerMapper;

  @BeforeEach
  void beforeAll() {
    getWorkoutSetGroupsController =
        new GetWorkoutSetGroupsController(
            getWorkoutSetGroupsService, getWorkoutSetGroupsControllerMapper);
  }

  @Test
  void get() {
    final GetWorkoutSetGroupsResponseApplication getWorkoutSetGroupsResponseApplication =
        easyRandom.nextObject(GetWorkoutSetGroupsResponseApplication.class);

    when(getWorkoutSetGroupsService.execute(getWorkoutSetGroupsResponseApplication.getId()))
        .thenReturn(getWorkoutSetGroupsResponseApplication);

    final GetWorkoutSetGroupsResponseInfrastructure getWorkoutSetGroupsResponseInfrastructure =
        getWorkoutSetGroupsController.get(getWorkoutSetGroupsResponseApplication.getId()).getBody();
    assertEquals(
        getWorkoutSetGroupsResponseApplication.getId(),
        getWorkoutSetGroupsResponseInfrastructure.getId());
    assertEquals(
        getWorkoutSetGroupsResponseApplication.getUserId(),
        getWorkoutSetGroupsResponseInfrastructure.getUserId());
    assertEquals(
        getWorkoutSetGroupsResponseApplication.getDescription(),
        getWorkoutSetGroupsResponseInfrastructure.getDescription());
    assertEquals(
        getWorkoutSetGroupsResponseApplication.getDate(),
        getWorkoutSetGroupsResponseInfrastructure.getDate());
    assertEquals(
        getWorkoutSetGroupsResponseApplication.getDate(),
        getWorkoutSetGroupsResponseInfrastructure.getDate());
    assertEquals(
        getWorkoutSetGroupsResponseApplication.getSetGroups().size(),
        getWorkoutSetGroupsResponseInfrastructure.getSetGroups().size());

    final GetWorkoutSetGroupsResponseApplication.SetGroup setGroupExpected =
        getWorkoutSetGroupsResponseApplication.getSetGroups().get(0);
    final GetWorkoutSetGroupsResponseInfrastructure.SetGroup setGroupResult =
        getWorkoutSetGroupsResponseInfrastructure.getSetGroups().get(0);
    assertEquals(setGroupExpected.getDescription(), setGroupResult.getDescription());
    assertEquals(setGroupExpected.getListOrder(), setGroupResult.getListOrder());
    assertEquals(setGroupExpected.getExerciseId(), setGroupResult.getExerciseId());
    assertEquals(setGroupExpected.getId(), setGroupResult.getId());
  }
}
