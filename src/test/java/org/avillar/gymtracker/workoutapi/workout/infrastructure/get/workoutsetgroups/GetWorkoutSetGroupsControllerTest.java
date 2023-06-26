package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsetgroups;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.GetWorkoutSetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.model.GetWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsetgroups.mapper.GetWorkoutSetGroupsControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsetgroups.model.GetWorkoutSetGroupsResponseInfrastructure;
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
  }
}
