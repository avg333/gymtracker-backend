package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.set.getset.application.GetSetService;
import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper.GetSetControllerMapper;
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
class GetSetControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetSetControllerImpl getSetControllerImpl;

  @Mock private GetSetService getSetService;

  @Spy
  private final GetSetControllerMapper getSetControllerMapper =
      Mappers.getMapper(GetSetControllerMapper.class);

  @Test
  void getOk() {
    final GetSetResponseApplication expected =
        easyRandom.nextObject(GetSetResponseApplication.class);

    when(getSetService.execute(expected.getId())).thenReturn(expected);

    assertThat(getSetControllerImpl.execute(expected.getId()))
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
