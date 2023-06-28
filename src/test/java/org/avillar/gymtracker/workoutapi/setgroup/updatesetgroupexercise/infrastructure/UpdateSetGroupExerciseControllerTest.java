package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure;

import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application.UpdateSetGroupExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseRequestInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupExerciseControllerTest {

  @InjectMocks private UpdateSetGroupExerciseController updateSetGroupExerciseController;

  @Mock private UpdateSetGroupExerciseService updateSetGroupExerciseService;

  @Test
  void updateSetGroupExercise() {
    final UUID setGroupId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final UpdateSetGroupExerciseRequestInfrastructure updateSetGroupExerciseRequestInfrastructure =
        new UpdateSetGroupExerciseRequestInfrastructure();
    updateSetGroupExerciseRequestInfrastructure.setExerciseId(exerciseId);

    when(updateSetGroupExerciseService.execute(setGroupId, exerciseId)).thenReturn(exerciseId);

    Assertions.assertEquals(
        exerciseId,
        updateSetGroupExerciseController
            .patch(setGroupId, updateSetGroupExerciseRequestInfrastructure)
            .getBody()
            .getExerciseId());
  }
}
