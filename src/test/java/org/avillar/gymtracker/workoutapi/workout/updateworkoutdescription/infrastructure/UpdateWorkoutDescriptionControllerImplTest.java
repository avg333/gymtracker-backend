package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application.UpdateWorkoutDescriptionService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionRequest;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDescriptionControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private UpdateWorkoutDescriptionControllerImpl updateWorkoutDescriptionControllerImpl;

  @Mock private UpdateWorkoutDescriptionService updateWorkoutDescriptionService;

  @Test
  void updateWorkoutDescription() {
    final UUID workoutId = UUID.randomUUID();
    final UpdateWorkoutDescriptionRequest updateWorkoutDescriptionRequest =
        easyRandom.nextObject(UpdateWorkoutDescriptionRequest.class);

    when(updateWorkoutDescriptionService.execute(
            workoutId, updateWorkoutDescriptionRequest.getDescription()))
        .thenReturn(updateWorkoutDescriptionRequest.getDescription());

    final ResponseEntity<UpdateWorkoutDescriptionResponse> result =
        updateWorkoutDescriptionControllerImpl.execute(workoutId, updateWorkoutDescriptionRequest);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(
        updateWorkoutDescriptionRequest.getDescription(), result.getBody().getDescription());
  }
}
