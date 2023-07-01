package org.avillar.gymtracker.workoutapi.workout.getworkoutdateandid.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.application.GetWorkoutsDateAndIdService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.GetWorkoutsDateAndIdControllerImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponseInfrastructure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GetWorkoutsDateAndIdControllerImplTest {

  @InjectMocks private GetWorkoutsDateAndIdControllerImpl getWorkoutsDateAndIdControllerImpl;

  @Mock private GetWorkoutsDateAndIdService getWorkoutsDateAndIdService;

  @Test
  void getWorkoutsIdAndDateByUserAndExercise() {
    final Map<Date, UUID> expected = new HashMap<>();
    final Date dateKey = new Date();
    expected.put(dateKey, UUID.randomUUID());
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();

    when(getWorkoutsDateAndIdService.execute(userId, exerciseId)).thenReturn(expected);

    final ResponseEntity<GetWorkoutsDateAndIdResponseInfrastructure> result =
        getWorkoutsDateAndIdControllerImpl.execute(userId, exerciseId);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().getWorkoutsDateAndId().size());
    assertTrue(result.getBody().getWorkoutsDateAndId().containsKey(dateKey));
    assertEquals(expected.get(dateKey), result.getBody().getWorkoutsDateAndId().get(dateKey));
  }
}
