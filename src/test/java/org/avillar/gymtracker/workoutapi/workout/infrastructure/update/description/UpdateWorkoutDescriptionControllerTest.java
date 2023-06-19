package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.description;

import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.update.description.UpdateWorkoutDescriptionService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.description.model.UpdateWorkoutDescriptionRequestInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDescriptionControllerTest {

  @InjectMocks private UpdateWorkoutDescriptionController updateWorkoutDescriptionController;

  @Mock private UpdateWorkoutDescriptionService updateWorkoutDescriptionService;

  @Test
  void updateWorkoutDescription() {
    final String description = "Description example 54.";
    final UUID workoutId = UUID.randomUUID();
    final UpdateWorkoutDescriptionRequestInfrastructure
        updateWorkoutDescriptionRequestInfrastructure =
            new UpdateWorkoutDescriptionRequestInfrastructure();
    updateWorkoutDescriptionRequestInfrastructure.setDescription(description);

    when(updateWorkoutDescriptionService.update(workoutId, description)).thenReturn(description);

    Assertions.assertEquals(
        description,
        updateWorkoutDescriptionController
            .updateWorkoutDescription(workoutId, updateWorkoutDescriptionRequestInfrastructure)
            .getBody()
            .getDescription());
  }
}
