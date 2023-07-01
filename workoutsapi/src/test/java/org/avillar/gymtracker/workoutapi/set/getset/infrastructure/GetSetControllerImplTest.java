package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.set.getset.application.GetSetService;
import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper.GetSetControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GetSetControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetSetControllerImpl getSetControllerImpl;
  @Mock private GetSetService getSetService;
  @Spy private GetSetControllerMapperImpl getSetControllerMapper;

  @Test
  void getOk() {
    final GetSetResponseApplication expected =
        easyRandom.nextObject(GetSetResponseApplication.class);

    when(getSetService.execute(expected.getId())).thenReturn(expected);

    final ResponseEntity<GetSetResponseInfrastructure> result =
        getSetControllerImpl.execute(expected.getId());
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    Assertions.assertEquals(expected.getId(), result.getBody().getId());
    Assertions.assertEquals(expected.getListOrder(), result.getBody().getListOrder());
    Assertions.assertEquals(expected.getReps(), result.getBody().getReps());
    Assertions.assertEquals(expected.getRir(), result.getBody().getRir());
    Assertions.assertEquals(expected.getWeight(), result.getBody().getWeight());
    Assertions.assertEquals(expected.getDescription(), result.getBody().getDescription());
    Assertions.assertEquals(expected.getSetGroup().getId(), result.getBody().getSetGroup().getId());
  }
}