package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application.UpdateWorkoutDescriptionService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionRequestInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDescriptionControllerImplTest {

  @InjectMocks private UpdateWorkoutDescriptionControllerImpl updateWorkoutDescriptionControllerImpl;

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

    Assertions.assertEquals(
        description,
        updateWorkoutDescriptionControllerImpl
            .execute(workoutId, updateWorkoutDescriptionRequestInfrastructure)
            .getBody()
            .getDescription());
  }
}
