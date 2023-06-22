package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.idanddate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.get.idanddate.GetWorkoutIdAndDateService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.idanddate.model.GetWorkoutIdAndDateResponseInfrastructure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutIdAndDateControllerTest {

  private final UUID userId = UUID.randomUUID();

  private Map<Date, UUID> serviceResponse;

  @InjectMocks private GetWorkoutIdAndDateController getWorkoutIdAndDateController;

  @Mock private GetWorkoutIdAndDateService getWorkoutIdAndDateService;

  @BeforeEach
  void beforeAll() {
    serviceResponse = new HashMap<>();
    serviceResponse.put(new Date(), UUID.randomUUID());
  }

  @Test
  void getWorkoutsIdAndDateByUser() {
    when(getWorkoutIdAndDateService.getAllUserWorkoutDates(userId)).thenReturn(serviceResponse);

    final GetWorkoutIdAndDateResponseInfrastructure getWorkoutIdAndDateResponseInfrastructure =
        getWorkoutIdAndDateController.get(userId, null).getBody();

    assertEquals(serviceResponse, getWorkoutIdAndDateResponseInfrastructure.getWorkoutsIdAndDate());
  }

  @Test
  void getWorkoutsIdAndDateByUserAndExercise() {
    final UUID exerciseId = UUID.randomUUID();

    when(getWorkoutIdAndDateService.getAllUserWorkoutsWithExercise(userId, exerciseId))
        .thenReturn(serviceResponse);

    final GetWorkoutIdAndDateResponseInfrastructure getWorkoutIdAndDateResponseInfrastructure =
        getWorkoutIdAndDateController.get(userId, exerciseId).getBody();

    assertEquals(serviceResponse, getWorkoutIdAndDateResponseInfrastructure.getWorkoutsIdAndDate());
  }
}
