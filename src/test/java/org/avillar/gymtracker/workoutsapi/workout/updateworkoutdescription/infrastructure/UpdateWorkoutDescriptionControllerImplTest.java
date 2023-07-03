package org.avillar.gymtracker.workoutsapi.workout.updateworkoutdescription.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application.UpdateWorkoutDescriptionService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.UpdateWorkoutDescriptionControllerImpl;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionResponseInfrastructure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDescriptionControllerImplTest {

  @InjectMocks
  private UpdateWorkoutDescriptionControllerImpl updateWorkoutDescriptionControllerImpl;

  @Mock private UpdateWorkoutDescriptionService updateWorkoutDescriptionService;

  @Test
  void updateWorkoutDescription() {
    final String description = "Description example 54.";
    final UUID workoutId = UUID.randomUUID();
    final UpdateWorkoutDescriptionRequestInfrastructure
        updateWorkoutDescriptionRequestInfrastructure =
            new UpdateWorkoutDescriptionRequestInfrastructure();
    updateWorkoutDescriptionRequestInfrastructure.setDescription(description);

    when(updateWorkoutDescriptionService.execute(workoutId, description)).thenReturn(description);

    final ResponseEntity<UpdateWorkoutDescriptionResponseInfrastructure> result =
        updateWorkoutDescriptionControllerImpl.execute(
            workoutId, updateWorkoutDescriptionRequestInfrastructure);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(description, result.getBody().getDescription());
    verify(updateWorkoutDescriptionService).execute(workoutId, description);
  }
}
