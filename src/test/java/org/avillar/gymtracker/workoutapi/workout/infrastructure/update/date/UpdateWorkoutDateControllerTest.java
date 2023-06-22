package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.date;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.update.date.UpdateWorkoutDateService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.date.model.UpdateWorkoutDateRequestInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDateControllerTest {

  @InjectMocks private UpdateWorkoutDateController updateWorkoutDateController;

  @Mock private UpdateWorkoutDateService updateWorkoutDateService;

  @Test
  void updateWorkoutDate() {
    final Date date = new Date();
    final UUID workoutId = UUID.randomUUID();
    final UpdateWorkoutDateRequestInfrastructure updateWorkoutDateRequestInfrastructure =
        new UpdateWorkoutDateRequestInfrastructure();
    updateWorkoutDateRequestInfrastructure.setDate(date);

    when(updateWorkoutDateService.execute(workoutId, date)).thenReturn(date);

    Assertions.assertEquals(
        date,
        updateWorkoutDateController
            .patch(workoutId, updateWorkoutDateRequestInfrastructure)
            .getBody()
            .getDate());
  }
}
