package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.GetSetGroupsByExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.mapper.GetSetGroupsByExerciseControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponse;
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
class GetSetGroupsByExerciseControllerImplTest {

  @InjectMocks private GetSetGroupsByExerciseControllerImpl controller;

  @Mock private GetSetGroupsByExerciseService service;
  @Mock private GetSetGroupsByExerciseControllerMapper mapper;

  @Test
  void shouldGetSetGroupsByExerciseSuccessfully() throws WorkoutIllegalAccessException {
    final UUID userId = UUID.randomUUID();
    final UUID exerciseId = UUID.randomUUID();
    final List<SetGroup> response = Instancio.createList(SetGroup.class);
    final List<GetSetGroupsByExerciseResponse> responseDto =
        Instancio.createList(GetSetGroupsByExerciseResponse.class);

    when(service.execute(userId, exerciseId)).thenReturn(response);
    when(mapper.map(response)).thenReturn(responseDto);

    assertThat(controller.execute(userId, exerciseId)).isEqualTo(responseDto);
  }
}
