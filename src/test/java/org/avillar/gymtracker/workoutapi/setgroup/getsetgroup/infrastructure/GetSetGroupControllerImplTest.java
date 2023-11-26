package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.GetSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper.GetSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponse;
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
class GetSetGroupControllerImplTest {

  @InjectMocks private GetSetGroupControllerImpl controller;

  @Mock private GetSetGroupService service;
  @Mock private GetSetGroupControllerMapper mapper;

  @Test
  void shouldGetSetSetGroupSuccessfully()
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final UUID setGroupId = UUID.randomUUID();
    final SetGroup response = Instancio.create(SetGroup.class);
    final GetSetGroupResponse responseDto = Instancio.create(GetSetGroupResponse.class);

    when(service.execute(setGroupId)).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(setGroupId)).isEqualTo(responseDto);
  }
}
