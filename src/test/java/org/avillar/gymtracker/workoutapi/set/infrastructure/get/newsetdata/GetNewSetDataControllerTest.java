package org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.GetNewSetDataService;
import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.model.GetNewSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata.mapper.GetNewSetDataControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.newsetdata.model.GetNewSetDataResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetNewSetDataControllerTest {

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
    final String description = "Description example 54.";
    final int reps = 6;
    final double rir = 7.6;
    final double weight = 75.0;

    when(getNewSetDataService.getNewSetData(setId))
        .thenReturn(new GetNewSetDataResponseApplication(description, reps, rir, weight));

    final GetNewSetDataResponseInfrastructure getNewSetDataResponseInfrastructure =
        getNewSetDataController.getSetDefaultDataForNewSet(setId).getBody();
    Assertions.assertEquals(description, getNewSetDataResponseInfrastructure.getDescription());
    Assertions.assertEquals(reps, getNewSetDataResponseInfrastructure.getReps());
    Assertions.assertEquals(rir, getNewSetDataResponseInfrastructure.getRir());
    Assertions.assertEquals(weight, getNewSetDataResponseInfrastructure.getWeight());
  }
}
