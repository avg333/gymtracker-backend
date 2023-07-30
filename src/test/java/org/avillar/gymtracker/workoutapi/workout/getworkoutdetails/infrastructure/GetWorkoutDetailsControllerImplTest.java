package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.GetWorkoutDetailsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper.GetWorkoutDetailsControllerMapper;
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
class GetWorkoutDetailsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutDetailsControllerImpl getWorkoutDetailsControllerImpl;

  @Mock private GetWorkoutDetailsService getWorkoutDetailsService;

  @Spy
  private final GetWorkoutDetailsControllerMapper getWorkoutControllerMapper =
      Mappers.getMapper(GetWorkoutDetailsControllerMapper.class);

  @Test
  void get() {
    final GetWorkoutDetailsResponseApplication expected =
        easyRandom.nextObject(GetWorkoutDetailsResponseApplication.class);

    when(getWorkoutDetailsService.execute(expected.getId())).thenReturn(expected);

    assertThat(getWorkoutDetailsControllerImpl.execute(expected.getId()))
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
