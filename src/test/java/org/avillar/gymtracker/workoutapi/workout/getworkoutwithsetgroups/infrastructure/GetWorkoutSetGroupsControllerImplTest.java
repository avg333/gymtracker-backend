package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.GetWorkoutSetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.model.GetWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.mapper.GetWorkoutSetGroupsControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GetWorkoutSetGroupsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutSetGroupsControllerImpl getWorkoutSetGroupsControllerImpl;

  @Mock private GetWorkoutSetGroupsService getWorkoutSetGroupsService;
  @Spy private GetWorkoutSetGroupsControllerMapperImpl getWorkoutSetGroupsControllerMapper;

  @Test
  void get() {
    final GetWorkoutSetGroupsResponseApplication expected =
        easyRandom.nextObject(GetWorkoutSetGroupsResponseApplication.class);

    when(getWorkoutSetGroupsService.execute(expected.getId())).thenReturn(expected);

    final ResponseEntity<GetWorkoutSetGroupsResponseInfrastructure> response =
        getWorkoutSetGroupsControllerImpl.get(expected.getId());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    verify(getWorkoutSetGroupsService).execute(expected.getId());
    assertEquals(expected.getId(), response.getBody().getId());
    assertEquals(expected.getUserId(), response.getBody().getUserId());
    assertEquals(expected.getDescription(), response.getBody().getDescription());
    assertEquals(expected.getDate(), response.getBody().getDate());
    assertEquals(expected.getDate(), response.getBody().getDate());
    assertEquals(expected.getSetGroups().size(), response.getBody().getSetGroups().size());

    if (!response.getBody().getSetGroups().isEmpty()) {
      assertEquals(
          expected.getSetGroups().get(0).getDescription(),
          response.getBody().getSetGroups().get(0).getDescription());
      assertEquals(
          expected.getSetGroups().get(0).getListOrder(),
          response.getBody().getSetGroups().get(0).getListOrder());
      assertEquals(
          expected.getSetGroups().get(0).getExerciseId(),
          response.getBody().getSetGroups().get(0).getExerciseId());
      assertEquals(
          expected.getSetGroups().get(0).getId(), response.getBody().getSetGroups().get(0).getId());
    }
  }
}
