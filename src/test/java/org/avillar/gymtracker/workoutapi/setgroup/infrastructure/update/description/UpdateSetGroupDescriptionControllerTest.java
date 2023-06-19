package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.description;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.description.UpdateSetGroupDescriptionService;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.description.model.UpdateSetGroupDescriptionRequestInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupDescriptionControllerTest {

  @InjectMocks private UpdateSetGroupDescriptionController updateSetGroupDescriptionController;

  @Mock private UpdateSetGroupDescriptionService updateSetGroupDescriptionService;

  @Test
  void putSetGroup() {
    final String description = "Description example 54.";
    final UUID workoutId = UUID.randomUUID();
    final UpdateSetGroupDescriptionRequestInfrastructure
        updateWorkoutDescriptionRequestInfrastructure =
            new UpdateSetGroupDescriptionRequestInfrastructure();
    updateWorkoutDescriptionRequestInfrastructure.setDescription(description);

    when(updateSetGroupDescriptionService.update(workoutId, description)).thenReturn(description);

    Assertions.assertEquals(
        description,
        updateSetGroupDescriptionController
            .updateSetGroupDescription(workoutId, updateWorkoutDescriptionRequestInfrastructure)
            .getBody()
            .getDescription());
  }
}
