package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.application.UpdateWorkoutDateService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateRequest;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class UpdateWorkoutDateControllerImplTest {

  @InjectMocks private UpdateWorkoutDateControllerImpl controller;

  @Mock private UpdateWorkoutDateService service;

  @Test
  void shouldUpdateDateSuccessfully()
      throws WorkoutNotFoundException,
          WorkoutIllegalAccessException,
          WorkoutForDateAlreadyExistsException {
    final UUID workoutId = UUID.randomUUID();
    final UpdateWorkoutDateRequest request = Instancio.create(UpdateWorkoutDateRequest.class);
    final LocalDate response = Instancio.create(LocalDate.class);

    when(service.execute(workoutId, request.date())).thenReturn(response);

    final UpdateWorkoutDateResponse result = controller.execute(workoutId, request);
    assertThat(result).isNotNull();
    assertThat(result.date()).isEqualTo(response);
  }
}
