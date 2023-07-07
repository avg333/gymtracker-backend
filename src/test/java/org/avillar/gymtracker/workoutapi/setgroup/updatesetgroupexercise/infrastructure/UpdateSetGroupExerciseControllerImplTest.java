package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application.UpdateSetGroupExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupExerciseControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetGroupExerciseControllerImpl updateSetGroupExerciseControllerImpl;

  @Mock private UpdateSetGroupExerciseService updateSetGroupExerciseService;

  @Test
  void updateSetGroupExercise() {
    final UUID setGroupId = UUID.randomUUID();
    final UpdateSetGroupExerciseRequest expected =
        easyRandom.nextObject(UpdateSetGroupExerciseRequest.class);

    when(updateSetGroupExerciseService.execute(setGroupId, expected.getExerciseId()))
        .thenReturn(expected.getExerciseId());

    final ResponseEntity<UpdateSetGroupExerciseResponse> result =
        updateSetGroupExerciseControllerImpl.execute(setGroupId, expected);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.getExerciseId(), result.getBody().getExerciseId());
  }
}
