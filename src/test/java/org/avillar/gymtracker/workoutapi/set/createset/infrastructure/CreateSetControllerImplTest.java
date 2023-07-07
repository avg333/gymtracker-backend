package org.avillar.gymtracker.workoutapi.set.createset.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.set.createset.application.CreateSetService;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper.CreateSetControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponse;
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
class CreateSetControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateSetControllerImpl postSetControllerImpl;

  @Mock private CreateSetService createSetService;
  @Spy private CreateSetControllerMapperImpl createSetControllerMapper;

  @Test
  void createOk() {
    final CreateSetResponseApplication expected =
        easyRandom.nextObject(CreateSetResponseApplication.class);
    final CreateSetRequest createSetRequest = new CreateSetRequest();
    createSetRequest.setReps(expected.getReps());
    createSetRequest.setRir(expected.getRir());
    createSetRequest.setWeight(expected.getWeight());
    createSetRequest.setDescription(expected.getDescription());
    createSetRequest.setCompleted(expected.getCompletedAt() != null);

    when(createSetService.execute(
            expected.getSetGroup().getId(), createSetControllerMapper.map(createSetRequest)))
        .thenReturn(expected);

    final ResponseEntity<CreateSetResponse> result =
        postSetControllerImpl.execute(expected.getSetGroup().getId(), createSetRequest);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody().getId()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
