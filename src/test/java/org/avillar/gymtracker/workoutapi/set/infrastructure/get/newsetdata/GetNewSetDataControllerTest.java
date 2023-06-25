package org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata;

import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.GetNewSetDataService;
import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.model.GetNewSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata.mapper.GetNewSetDataControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata.model.GetNewSetDataResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetNewSetDataControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private GetNewSetDataController getNewSetDataController;
  @Mock private GetNewSetDataService getNewSetDataService;
  @Spy private GetNewSetDataControllerMapperImpl getNewSetDataControllerMapper;

  @BeforeEach
  void beforeEach() {
    getNewSetDataController =
        new GetNewSetDataController(getNewSetDataService, getNewSetDataControllerMapper);
  }

  @Test
  void getOk() {
    final UUID setId = UUID.randomUUID();
    final GetNewSetDataResponseApplication getNewSetDataResponseApplication =
        easyRandom.nextObject(GetNewSetDataResponseApplication.class);

    when(getNewSetDataService.execute(setId)).thenReturn(getNewSetDataResponseApplication);

    final GetNewSetDataResponseInfrastructure getNewSetDataResponseInfrastructure =
        getNewSetDataController.get(setId).getBody();
    Assertions.assertEquals(
        getNewSetDataResponseApplication.getDescription(),
        getNewSetDataResponseInfrastructure.getDescription());
    Assertions.assertEquals(
        getNewSetDataResponseApplication.getReps(), getNewSetDataResponseInfrastructure.getReps());
    Assertions.assertEquals(
        getNewSetDataResponseApplication.getRir(), getNewSetDataResponseInfrastructure.getRir());
    Assertions.assertEquals(
        getNewSetDataResponseApplication.getWeight(),
        getNewSetDataResponseInfrastructure.getWeight());
  }
}
