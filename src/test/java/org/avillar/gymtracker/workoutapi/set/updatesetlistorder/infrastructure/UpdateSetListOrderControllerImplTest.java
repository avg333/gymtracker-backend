package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.UpdateSetListOrderService;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.mapper.UpdateSetListOrderControllerMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponse;
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
class UpdateSetListOrderControllerImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetListOrderControllerImpl updateSetListOrderControllerImpl;

  @Mock private UpdateSetListOrderService updateSetListOrderService;

  @Spy
  private final UpdateSetListOrderControllerMapper updateSetListOrderControllerMapper =
      Mappers.getMapper(UpdateSetListOrderControllerMapper.class);

  @Test
  void updateSetData() {
    final UUID setId = UUID.randomUUID();
    final UpdateSetListOrderRequest updateSetListOrderRequest =
        easyRandom.nextObject(UpdateSetListOrderRequest.class);
    final List<UpdateSetListOrderResponseApplication> expected =
        easyRandom.objects(UpdateSetListOrderResponseApplication.class, LIST_SIZE).toList();

    when(updateSetListOrderService.execute(setId, updateSetListOrderRequest.getListOrder()))
        .thenReturn(expected);

    final List<UpdateSetListOrderResponse> result =
        updateSetListOrderControllerImpl.execute(setId, updateSetListOrderRequest);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
