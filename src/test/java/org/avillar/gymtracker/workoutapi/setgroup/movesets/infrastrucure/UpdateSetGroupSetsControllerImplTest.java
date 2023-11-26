package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.application.UpdateSetGroupSetsService;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.mapper.UpdateSetGroupSetsControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsRequest;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponse;
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
class UpdateSetGroupSetsControllerImplTest {

  @InjectMocks private UpdateSetGroupSetsControllerImpl updateSetGroupSetsController;

  @Mock private UpdateSetGroupSetsService updateSetGroupSetsService;
  @Mock private UpdateSetGroupSetsControllerMapper updateSetGroupSetsControllerMapper;

  @Test
  void shouldMoveSetsSuccessfully()
      throws WorkoutIllegalAccessException, SetGroupNotFoundException {
    final UUID setGroupId = UUID.randomUUID();
    final UpdateSetGroupSetsRequest request = Instancio.create(UpdateSetGroupSetsRequest.class);
    final List<Set> response = Instancio.createList(Set.class);
    final List<UpdateSetGroupSetsResponse> expected =
        Instancio.createList(UpdateSetGroupSetsResponse.class);

    when(updateSetGroupSetsService.execute(setGroupId, request.setGroupId())).thenReturn(response);
    when(updateSetGroupSetsControllerMapper.map(response)).thenReturn(expected);

    assertThat(updateSetGroupSetsController.execute(setGroupId, request)).isEqualTo(expected);
  }
}
