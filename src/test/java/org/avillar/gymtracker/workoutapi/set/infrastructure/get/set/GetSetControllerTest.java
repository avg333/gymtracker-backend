package org.avillar.gymtracker.workoutapi.set.infrastructure.get.set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.get.set.GetSetService;
import org.avillar.gymtracker.workoutapi.set.application.get.set.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.application.get.set.model.GetSetResponseApplication.SetGroup;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.set.mapper.GetSetControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.infrastructure.get.set.model.GetSetResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetSetControllerTest {

  private GetSetController getSetController;
  @Mock private GetSetService getSetService;
  @Spy private GetSetControllerMapperImpl getSetControllerMapper;

  @BeforeEach
  void beforeEach() {
    getSetController = new GetSetController(getSetService, getSetControllerMapper);
  }

  @Test
  void getOk() {
    final UUID setId = UUID.randomUUID();
    final int listOrder = 3;
    final int reps = 6;
    final double rir = 7.6;
    final double weight = 75.0;

    when(getSetService.getSet(setId))
        .thenReturn(
            new GetSetResponseApplication(
                setId, listOrder, null, reps, rir, weight, new SetGroup()));

    final GetSetResponseInfrastructure getSetResponseInfrastructure =
        getSetController.getSet(setId).getBody();
    Assertions.assertEquals(setId, getSetResponseInfrastructure.getId());
    Assertions.assertEquals(listOrder, getSetResponseInfrastructure.getListOrder());
    Assertions.assertEquals(reps, getSetResponseInfrastructure.getReps());
    Assertions.assertEquals(rir, getSetResponseInfrastructure.getRir());
    Assertions.assertEquals(weight, getSetResponseInfrastructure.getWeight());
  }
}
