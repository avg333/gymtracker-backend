package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application.UpdateWorkoutDateService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDateControllerImplTest {

  @InjectMocks private UpdateWorkoutDateControllerImpl updateWorkoutDateControllerImpl;

  @Mock private UpdateWorkoutDateService updateWorkoutDateService;

  @Test
  void updateWorkoutDate() {
    final Date date = new Date();
    final UUID workoutId = UUID.randomUUID();
    final UpdateWorkoutDateRequestInfrastructure updateWorkoutDateRequestInfrastructure =
        new UpdateWorkoutDateRequestInfrastructure();
    updateWorkoutDateRequestInfrastructure.setDate(date);

    when(updateWorkoutDateService.execute(workoutId, date)).thenReturn(date);

    final ResponseEntity<UpdateWorkoutDateResponseInfrastructure> response =
        updateWorkoutDateControllerImpl.execute(workoutId, updateWorkoutDateRequestInfrastructure);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(date, response.getBody().getDate());
  }
}
