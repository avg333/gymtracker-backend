package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper.UpdateSetDataControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateSetDataControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetDataControllerImpl updateSetDataControllerImpl;

  @Mock private UpdateSetDataService updateSetDataService;
  @Spy private UpdateSetDataControllerMapperImpl updateSetDataControllerMapper;

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

    when(updateSetDataService.execute(
            setId, updateSetDataControllerMapper.map(updateSetDataRequest)))
        .thenReturn(expected);

    final ResponseEntity<UpdateSetDataResponse> result =
        updateSetDataControllerImpl.execute(setId, updateSetDataRequest);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
