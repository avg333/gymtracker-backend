package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.GetNewSetDataService;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.model.GetNewSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.mapper.GetNewSetDataControllerMapper;
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
class GetNewSetDataControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetNewSetDataControllerImpl getNewSetDataControllerImpl;

  @Mock private GetNewSetDataService getNewSetDataService;

  @Spy
  private final GetNewSetDataControllerMapper getNewSetDataControllerMapper =
      Mappers.getMapper(GetNewSetDataControllerMapper.class);

  @Test
  void getOk() {
    final UUID setId = UUID.randomUUID();
    final GetNewSetDataResponseApplication expected =
        easyRandom.nextObject(GetNewSetDataResponseApplication.class);

    when(getNewSetDataService.execute(setId)).thenReturn(expected);

    assertThat(getNewSetDataControllerImpl.execute(setId))
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
