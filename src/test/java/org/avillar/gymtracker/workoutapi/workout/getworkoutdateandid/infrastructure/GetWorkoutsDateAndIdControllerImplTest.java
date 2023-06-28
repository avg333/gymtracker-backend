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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutsDateAndIdControllerImplTest {

  private final UUID userId = UUID.randomUUID();

  private Map<Date, UUID> serviceResponse;

  @InjectMocks private GetWorkoutsDateAndIdControllerImpl getWorkoutsDateAndIdControllerImpl;

  @Mock private GetWorkoutsDateAndIdService getWorkoutsDateAndIdService;

  @BeforeEach
  void beforeAll() {
    serviceResponse = new HashMap<>();
    serviceResponse.put(new Date(), UUID.randomUUID());
  }

  @Test
  void getWorkoutsIdAndDateByUserAndExercise() {
    final UUID exerciseId = UUID.randomUUID();

    when(getWorkoutsDateAndIdService.execute(userId, exerciseId)).thenReturn(serviceResponse);

    final GetWorkoutsDateAndIdResponseInfrastructure getWorkoutsDateAndIdResponseInfrastructure =
        getWorkoutsDateAndIdControllerImpl.execute(userId, exerciseId).getBody();

    assertEquals(
        serviceResponse, getWorkoutsDateAndIdResponseInfrastructure.getWorkoutsDateAndId());
  }
}
