package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.GetWorkoutDetailsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper.GetWorkoutDetailsControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetWorkoutDetailsControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetWorkoutDetailsControllerImpl getWorkoutDetailsControllerImpl;

  @Mock private GetWorkoutDetailsService getWorkoutDetailsService;
  @Spy private GetWorkoutDetailsControllerMapperImpl getWorkoutControllerMapper;

  @BeforeEach
  void beforeAll() {
    getWorkoutDetailsControllerImpl = new GetWorkoutDetailsControllerImpl(getWorkoutDetailsService, getWorkoutControllerMapper);
  }

  @Test
  void get() {
    final GetWorkoutDetailsResponseApplication getWorkoutDetailsResponseApplication =
        easyRandom.nextObject(GetWorkoutDetailsResponseApplication.class);

    when(getWorkoutDetailsService.execute(getWorkoutDetailsResponseApplication.getId()))
        .thenReturn(getWorkoutDetailsResponseApplication);

    final GetWorkoutDetailsResponseInfrastructure getWorkoutDetailsResponseInfrastructure =
        getWorkoutDetailsControllerImpl.execute(getWorkoutDetailsResponseApplication.getId()).getBody();
    assertEquals(getWorkoutDetailsResponseApplication.getId(), getWorkoutDetailsResponseInfrastructure.getId());
    assertEquals(
        getWorkoutDetailsResponseApplication.getUserId(), getWorkoutDetailsResponseInfrastructure.getUserId());
    assertEquals(
        getWorkoutDetailsResponseApplication.getDescription(),
        getWorkoutDetailsResponseInfrastructure.getDescription());
    assertEquals(
        getWorkoutDetailsResponseApplication.getDate(), getWorkoutDetailsResponseInfrastructure.getDate());
    assertEquals(
        getWorkoutDetailsResponseApplication.getDate(), getWorkoutDetailsResponseInfrastructure.getDate());
    assertEquals(
        getWorkoutDetailsResponseApplication.getSetGroups().size(),
        getWorkoutDetailsResponseInfrastructure.getSetGroups().size());
  }
}
