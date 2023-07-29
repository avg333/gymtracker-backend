package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.GetWorkoutSetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.model.GetWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.mapper.GetWorkoutSetGroupsControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponse;
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
class GetWorkoutSetGroupsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetWorkoutSetGroupsControllerImpl getWorkoutSetGroupsControllerImpl;

  @Mock private GetWorkoutSetGroupsService getWorkoutSetGroupsService;
  @Spy private GetWorkoutSetGroupsControllerMapperImpl getWorkoutSetGroupsControllerMapper;

  @Test
  void get() {
    final GetWorkoutSetGroupsResponseApplication expected =
        easyRandom.nextObject(GetWorkoutSetGroupsResponseApplication.class);

    when(getWorkoutSetGroupsService.execute(expected.getId())).thenReturn(expected);

    final ResponseEntity<GetWorkoutSetGroupsResponse> result =
        getWorkoutSetGroupsControllerImpl.get(expected.getId());
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody().getId()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
