package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application.UpdateSetGroupExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseResponse;
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
class UpdateSetGroupExerciseControllerImplTest {

  @InjectMocks private UpdateSetGroupExerciseControllerImpl controller;

  @Mock private UpdateSetGroupExerciseService service;

  @Test
  void shouldUpdateExerciseIdSuccessfully()
      throws SetGroupNotFoundException,
          WorkoutIllegalAccessException,
          ExerciseUnavailableException {
    final UUID setGroupId = UUID.randomUUID();
    final UpdateSetGroupExerciseRequest request =
        Instancio.create(UpdateSetGroupExerciseRequest.class);
    final UUID response = UUID.randomUUID();

    when(service.execute(setGroupId, request.exerciseId())).thenReturn(response);

    final UpdateSetGroupExerciseResponse result = controller.execute(setGroupId, request);
    assertThat(result).isNotNull();
    assertThat(result.exerciseId()).isEqualTo(response);
  }
}
