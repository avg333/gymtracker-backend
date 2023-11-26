package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.CreateSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper.CreateSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;
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
class CreateGetSetGroupControllerTest {

  @InjectMocks private CreateSetGroupControllerImpl controller;

  @Mock private CreateSetGroupService service;
  @Mock private CreateSetGroupControllerMapper mapper;

  @Test
  void shouldCreateSetGroupSuccessfully()
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    final UUID workoutId = UUID.randomUUID();
    final CreateSetGroupRequest requestDto = Instancio.create(CreateSetGroupRequest.class);
    final SetGroup request = Instancio.create(SetGroup.class);
    final SetGroup response = Instancio.create(SetGroup.class);
    final CreateSetGroupResponse responseDto = Instancio.create(CreateSetGroupResponse.class);

    when(mapper.map(requestDto)).thenReturn(request);
    when(service.execute(workoutId, request)).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(workoutId, requestDto)).isEqualTo(responseDto);
  }
}
