package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkout.application.GetWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper.GetWorkoutControllerMapper;
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
class GetWorkoutControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutControllerImpl getWorkoutControllerImpl;

  @Mock private GetWorkoutService getWorkoutServiceService;

  @Spy
  private final GetWorkoutControllerMapper getWorkoutSimpleControllerMapper =
      Mappers.getMapper(GetWorkoutControllerMapper.class);

  @Test
  void get() {
    final GetWorkoutResponseApplication expected =
        easyRandom.nextObject(GetWorkoutResponseApplication.class);

    when(getWorkoutServiceService.execute(expected.getId())).thenReturn(expected);

    assertThat(getWorkoutControllerImpl.execute(expected.getId()))
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
