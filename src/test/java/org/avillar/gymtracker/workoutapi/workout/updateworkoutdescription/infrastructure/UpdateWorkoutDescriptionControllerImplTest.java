package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.application.UpdateWorkoutDescriptionService;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionRequest;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionResponse;
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
class UpdateWorkoutDescriptionControllerImplTest {

  @InjectMocks private UpdateWorkoutDescriptionControllerImpl controller;

  @Mock private UpdateWorkoutDescriptionService service;

  @Test
  void shouldUpdateDescriptionSuccessfully()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException {
    final UUID workoutId = UUID.randomUUID();
    final UpdateWorkoutDescriptionRequest request =
        Instancio.create(UpdateWorkoutDescriptionRequest.class);
    final String description = Instancio.create(String.class);

    when(service.execute(workoutId, request.description())).thenReturn(description);

    final UpdateWorkoutDescriptionResponse result = controller.execute(workoutId, request);
    assertThat(result).isNotNull();
    assertThat(result.description()).isEqualTo(description);
  }
}
