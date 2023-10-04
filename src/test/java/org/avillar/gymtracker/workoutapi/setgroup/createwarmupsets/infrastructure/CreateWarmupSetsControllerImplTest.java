package org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.CreateWarmupSetsService;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.model.CreateWarmupSetsRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.model.CreateWarmupSetsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.mapper.CreateWarmupSetsControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.model.CreateWarmupSetsRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateWarmupSetsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateWarmupSetsControllerImpl createWarmupSetsController;

  @Mock private CreateWarmupSetsService createWarmupSetsService;

  @Spy
  private CreateWarmupSetsControllerMapper createWarmupSetsControllerMapper =
      Mappers.getMapper(CreateWarmupSetsControllerMapper.class);

  @Test
  void executeTest() {
    final UUID setGroupId = UUID.randomUUID();
    final CreateWarmupSetsRequest createWarmupSetsRequest =
        easyRandom.nextObject(CreateWarmupSetsRequest.class);
    final CreateWarmupSetsResponseApplication expected =
        easyRandom.nextObject(CreateWarmupSetsResponseApplication.class);

    final ArgumentCaptor<CreateWarmupSetsRequestApplication>
        createWarmupSetsRequestApplicationCaptor =
            ArgumentCaptor.forClass(CreateWarmupSetsRequestApplication.class);
    when(createWarmupSetsService.execute(
            eq(setGroupId), createWarmupSetsRequestApplicationCaptor.capture()))
        .thenReturn(expected);

    final CreateWarmupSetsResponse result =
        createWarmupSetsController.execute(setGroupId, createWarmupSetsRequest);
    assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(expected);

    assertThat(createWarmupSetsRequestApplicationCaptor.getValue())
        .isNotNull()
        .usingRecursiveComparison()
        .isEqualTo(createWarmupSetsRequest);
  }
}
