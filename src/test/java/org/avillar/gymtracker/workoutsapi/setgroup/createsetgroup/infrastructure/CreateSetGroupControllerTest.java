package org.avillar.gymtracker.workoutsapi.setgroup.createsetgroup.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.CreateSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model.CreateSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.CreteSetGroupControllerImpl;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper.CreateSetGroupControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponseInfrastructure;
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
class CreateSetGroupControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreteSetGroupControllerImpl creteSetGroupControllerImpl;

  @Mock private CreateSetGroupService createSetGroupService;

  @Spy private CreateSetGroupControllerMapperImpl postSetGroupControllerMapper;

  @Test
  void post() {
    final CreateSetGroupResponseApplication expected =
        easyRandom.nextObject(CreateSetGroupResponseApplication.class);
    final CreateSetGroupRequestInfrastructure postWorkoutRequestInfrastructure =
        new CreateSetGroupRequestInfrastructure();
    postWorkoutRequestInfrastructure.setExerciseId(expected.getExerciseId());
    postWorkoutRequestInfrastructure.setDescription(expected.getDescription());

    when(createSetGroupService.execute(
            expected.getWorkout().getId(),
            postSetGroupControllerMapper.map(postWorkoutRequestInfrastructure)))
        .thenReturn(expected);

    final ResponseEntity<CreateSetGroupResponseInfrastructure> result =
        creteSetGroupControllerImpl.execute(
            expected.getWorkout().getId(), postWorkoutRequestInfrastructure);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertNotNull(result.getBody().getId());
    assertEquals(expected.getWorkout().getId(), result.getBody().getWorkout().getId());
    assertEquals(expected.getListOrder(), result.getBody().getListOrder());
    assertEquals(expected.getExerciseId(), result.getBody().getExerciseId());
    assertEquals(expected.getDescription(), result.getBody().getDescription());
  }
}
