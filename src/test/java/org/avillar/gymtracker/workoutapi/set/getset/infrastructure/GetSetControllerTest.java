package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.set.getset.application.GetSetService;
import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper.GetSetControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetSetControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetSetController getSetController;
  @Mock private GetSetService getSetService;
  @Spy private GetSetControllerMapperImpl getSetControllerMapper;

  @BeforeEach
  void beforeEach() {
    getSetController = new GetSetController(getSetService, getSetControllerMapper);
  }

  @Test
  void getOk() {
    final GetSetResponseApplication getSetResponseApplication =
        easyRandom.nextObject(GetSetResponseApplication.class);

    when(getSetService.execute(getSetResponseApplication.getId()))
        .thenReturn(getSetResponseApplication);

    final GetSetResponseInfrastructure getSetResponseInfrastructure =
        getSetController.get(getSetResponseApplication.getId()).getBody();
    Assertions.assertEquals(
        getSetResponseApplication.getId(), getSetResponseInfrastructure.getId());
    Assertions.assertEquals(
        getSetResponseApplication.getListOrder(), getSetResponseInfrastructure.getListOrder());
    Assertions.assertEquals(
        getSetResponseApplication.getReps(), getSetResponseInfrastructure.getReps());
    Assertions.assertEquals(
        getSetResponseApplication.getRir(), getSetResponseInfrastructure.getRir());
    Assertions.assertEquals(
        getSetResponseApplication.getWeight(), getSetResponseInfrastructure.getWeight());
    Assertions.assertEquals(
        getSetResponseApplication.getDescription(), getSetResponseInfrastructure.getDescription());
    Assertions.assertEquals(
        getSetResponseApplication.getSetGroup().getId(),
        getSetResponseInfrastructure.getSetGroup().getId());
  }
}
