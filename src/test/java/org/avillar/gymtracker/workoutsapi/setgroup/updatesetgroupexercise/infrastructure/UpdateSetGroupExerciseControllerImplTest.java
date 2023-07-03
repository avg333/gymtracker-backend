package org.avillar.gymtracker.workoutsapi.setgroup.updatesetgroupexercise.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application.UpdateSetGroupExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.UpdateSetGroupExerciseControllerImpl;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseResponseInfrastructure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupExerciseControllerImplTest {

  @InjectMocks private UpdateSetGroupExerciseControllerImpl updateSetGroupExerciseControllerImpl;

  @Mock private UpdateSetGroupExerciseService updateSetGroupExerciseService;

  @Test
  void updateSetGroupExercise() {
    final UUID setGroupId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final UpdateSetGroupExerciseRequestInfrastructure updateSetGroupExerciseRequestInfrastructure =
        new UpdateSetGroupExerciseRequestInfrastructure();
    updateSetGroupExerciseRequestInfrastructure.setExerciseId(exerciseId);

    when(updateSetGroupExerciseService.execute(setGroupId, exerciseId)).thenReturn(exerciseId);

    final ResponseEntity<UpdateSetGroupExerciseResponseInfrastructure> result =
        updateSetGroupExerciseControllerImpl.execute(
            setGroupId, updateSetGroupExerciseRequestInfrastructure);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(exerciseId, result.getBody().getExerciseId());
    verify(updateSetGroupExerciseService).execute(setGroupId, exerciseId);
  }
}
