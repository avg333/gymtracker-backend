package org.avillar.gymtracker.workoutsapi.set.createset.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.set.createset.application.CreateSetService;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.CreateSetControllerImpl;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper.CreateSetControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CreateSetControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private CreateSetControllerImpl postSetControllerImpl;

  @Mock private CreateSetService createSetService;

  @Spy private CreateSetControllerMapperImpl createSetControllerMapper;

  @Test
  void postSet() {
    final CreateSetResponseApplication expected =
        easyRandom.nextObject(CreateSetResponseApplication.class);
    final CreateSetRequestInfrastructure createSetRequestInfrastructure =
        new CreateSetRequestInfrastructure();
    createSetRequestInfrastructure.setReps(expected.getReps());
    createSetRequestInfrastructure.setRir(expected.getRir());
    createSetRequestInfrastructure.setWeight(expected.getWeight());
    createSetRequestInfrastructure.setDescription(expected.getDescription());

    when(createSetService.execute(
            expected.getSetGroup().getId(),
            createSetControllerMapper.map(createSetRequestInfrastructure)))
        .thenReturn(expected);

    final ResponseEntity<CreateSetResponseInfrastructure> result =
        postSetControllerImpl.execute(
            expected.getSetGroup().getId(), createSetRequestInfrastructure);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertNotNull(result.getBody().getId());
    assertEquals(expected.getSetGroup().getId(), result.getBody().getSetGroup().getId());
    assertEquals(expected.getListOrder(), result.getBody().getListOrder());
    assertEquals(expected.getDescription(), result.getBody().getDescription());
    assertEquals(expected.getWeight(), result.getBody().getWeight());
    assertEquals(expected.getRir(), result.getBody().getRir());
    assertEquals(expected.getReps(), result.getBody().getReps());
    assertEquals(expected.getId(), result.getBody().getId());
  }
}
