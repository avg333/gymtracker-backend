package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.GetWorkoutDetailsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper.GetWorkoutDetailsControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetWorkoutDetailsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutDetailsControllerImpl getWorkoutDetailsControllerImpl;

  @Mock private GetWorkoutDetailsService getWorkoutDetailsService;
  @Spy private GetWorkoutDetailsControllerMapperImpl getWorkoutControllerMapper;

  @Test
  void get() {
    final GetWorkoutDetailsResponseApplication expected =
        easyRandom.nextObject(GetWorkoutDetailsResponseApplication.class);

    when(getWorkoutDetailsService.execute(expected.getId())).thenReturn(expected);

    final ResponseEntity<GetWorkoutDetailsResponse> result =
        getWorkoutDetailsControllerImpl.execute(expected.getId());
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
