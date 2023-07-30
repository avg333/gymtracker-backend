package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.GetSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper.GetSetGroupControllerMapper;
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
class SetGroupControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private SetGroupControllerImpl setGroupControllerImpl;

  @Mock private GetSetGroupService getSetGroupService;

  @Spy
  private final GetSetGroupControllerMapper getSetGroupControllerMapper =
      Mappers.getMapper(GetSetGroupControllerMapper.class);

  @Test
  void getOk() {
    final GetSetGroupResponseApplication expected =
        easyRandom.nextObject(GetSetGroupResponseApplication.class);

    when(getSetGroupService.execute(expected.getId())).thenReturn(expected);

    assertThat(setGroupControllerImpl.execute(expected.getId()))
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
