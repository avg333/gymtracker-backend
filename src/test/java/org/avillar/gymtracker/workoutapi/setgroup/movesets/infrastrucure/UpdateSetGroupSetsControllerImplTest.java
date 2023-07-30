package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.application.UpdateSetGroupSetsService;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.application.model.UpdateSetGroupSetsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.mapper.UpdateSetGroupSetsControllereMapper;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class UpdateSetGroupSetsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();
  @Spy
  private final UpdateSetGroupSetsControllereMapper updateSetGroupSetsControllereMapper =
      Mappers.getMapper(UpdateSetGroupSetsControllereMapper.class);
  @InjectMocks private UpdateSetGroupSetsControllerImpl updateSetGroupSetsController;
  @Mock private UpdateSetGroupSetsService updateSetGroupSetsService;

  @Test
  void executeOk() {
    final UUID setGroupId = UUID.randomUUID();
    final UpdateSetGroupSetsRequestInfrastructure request =
        easyRandom.nextObject(UpdateSetGroupSetsRequestInfrastructure.class);
    final UpdateSetGroupSetsResponseApplication expected =
        easyRandom.nextObject(UpdateSetGroupSetsResponseApplication.class);

    when(updateSetGroupSetsService.execute(setGroupId, request.getSetGroupId()))
        .thenReturn(expected);

    final UpdateSetGroupSetsResponseInfrastructure response =
        updateSetGroupSetsController.execute(setGroupId, request);
    assertThat(response).isNotNull();
    assertThat(response.getSets()).size().isEqualTo(expected.getSets().size());
    assertThat(response).usingRecursiveComparison().isEqualTo(expected);
  }
}
