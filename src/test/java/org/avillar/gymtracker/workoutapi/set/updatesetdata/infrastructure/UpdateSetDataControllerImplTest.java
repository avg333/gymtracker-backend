package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper.UpdateSetDataControllerMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequest;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class UpdateSetDataControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetDataControllerImpl updateSetDataControllerImpl;

  @Mock private UpdateSetDataService updateSetDataService;

  @Spy
  private final UpdateSetDataControllerMapper updateSetDataControllerMapper =
      Mappers.getMapper(UpdateSetDataControllerMapper.class);

  @Test
  void updateSetData() {
    final UUID setId = UUID.randomUUID();
    final UpdateSetDataResponseApplication expected =
        easyRandom.nextObject(UpdateSetDataResponseApplication.class);
    final UpdateSetDataRequest updateSetDataRequest = new UpdateSetDataRequest();
    updateSetDataRequest.setDescription(expected.getDescription());
    updateSetDataRequest.setWeight(expected.getWeight());
    updateSetDataRequest.setRir(expected.getRir());
    updateSetDataRequest.setReps(expected.getReps());
    updateSetDataRequest.setCompleted(expected.getCompletedAt() != null);

    final ArgumentCaptor<UpdateSetDataRequestApplication> updateSetDataRequestApplicationCaptor =
        ArgumentCaptor.forClass(UpdateSetDataRequestApplication.class);

    when(updateSetDataService.execute(eq(setId), updateSetDataRequestApplicationCaptor.capture()))
        .thenReturn(expected);

    assertThat(updateSetDataControllerImpl.execute(setId, updateSetDataRequest))
        .usingRecursiveComparison()
        .isEqualTo(expected);
    assertThat(updateSetDataRequestApplicationCaptor.getValue())
        .usingRecursiveComparison()
        .isEqualTo(updateSetDataRequest);
  }
}
