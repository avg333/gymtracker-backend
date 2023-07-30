package org.avillar.gymtracker.workoutapi.set.createset.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.set.createset.application.CreateSetService;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper.CreateSetControllerMapper;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
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
class CreateSetControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateSetControllerImpl postSetControllerImpl;

  @Mock private CreateSetService createSetService;

  @Spy
  private final CreateSetControllerMapper createSetControllerMapper =
      Mappers.getMapper(CreateSetControllerMapper.class);

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

    assertThat(postSetControllerImpl.execute(expected.getSetGroup().getId(), createSetRequest))
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
