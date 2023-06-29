package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application.UpdateSetGroupDescriptionService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionResponseInfrastructure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupDescriptionControllerImplTest {

  @InjectMocks
  private UpdateSetGroupDescriptionControllerImpl updateSetGroupDescriptionControllerImpl;

  @Mock private UpdateSetGroupDescriptionService updateSetGroupDescriptionService;

  @Test
  void putSetGroup() {
    final String description = "Description example 54.";
    final UUID workoutId = UUID.randomUUID();
    final UpdateSetGroupDescriptionRequestInfrastructure
        updateWorkoutDescriptionRequestInfrastructure =
            new UpdateSetGroupDescriptionRequestInfrastructure();
    updateWorkoutDescriptionRequestInfrastructure.setDescription(description);

    when(updateSetGroupDescriptionService.execute(workoutId, description)).thenReturn(description);

    final ResponseEntity<UpdateSetGroupDescriptionResponseInfrastructure> result =
        updateSetGroupDescriptionControllerImpl.patch(
            workoutId, updateWorkoutDescriptionRequestInfrastructure);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(description, result.getBody().getDescription());
    verify(updateSetGroupDescriptionService).execute(workoutId, description);
  }
}
