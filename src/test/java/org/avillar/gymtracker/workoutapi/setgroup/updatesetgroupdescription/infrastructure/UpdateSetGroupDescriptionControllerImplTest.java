package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application.UpdateSetGroupDescriptionService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionResponse;
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
class UpdateSetGroupDescriptionControllerImplTest {

  @InjectMocks private UpdateSetGroupDescriptionControllerImpl controller;

  @Mock private UpdateSetGroupDescriptionService service;

  @Test
  void shouldUpdateDescriptionSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final UUID workoutId = UUID.randomUUID();
    final UpdateSetGroupDescriptionRequest request =
        Instancio.create(UpdateSetGroupDescriptionRequest.class);
    final String response = Instancio.create(String.class);

    when(service.execute(workoutId, request.description())).thenReturn(response);

    final UpdateSetGroupDescriptionResponse result = controller.execute(workoutId, request);
    assertThat(result).isNotNull();
    assertThat(result.description()).isEqualTo(response);
  }
}
