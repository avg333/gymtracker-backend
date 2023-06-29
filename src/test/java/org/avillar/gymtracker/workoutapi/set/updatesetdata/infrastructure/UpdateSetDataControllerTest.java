package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper.UpdateSetDataControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetDataControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private UpdateSetDataController updateSetDataController;
  @Mock private UpdateSetDataService updateSetDataService;
  @Spy private UpdateSetDataControllerMapperImpl updateSetDataControllerMapper;

  @BeforeEach
  void beforeEach() {
    updateSetDataController =
        new UpdateSetDataController(updateSetDataService, updateSetDataControllerMapper);
  }

  @Test
  void updateSetData() {
    final UpdateSetDataResponseApplication updateSetDataResponseApplication =
        easyRandom.nextObject(UpdateSetDataResponseApplication.class);
    final UUID setId = UUID.randomUUID();
    final UpdateSetDataRequestInfrastructure updateSetDataRequestInfrastructure =
        new UpdateSetDataRequestInfrastructure();
    updateSetDataRequestInfrastructure.setDescription(
        updateSetDataResponseApplication.getDescription());
    updateSetDataRequestInfrastructure.setWeight(updateSetDataResponseApplication.getWeight());
    updateSetDataRequestInfrastructure.setRir(updateSetDataResponseApplication.getRir());
    updateSetDataRequestInfrastructure.setReps(updateSetDataResponseApplication.getReps());

    when(updateSetDataService.execute(eq(setId), any(UpdateSetDataRequestApplication.class)))
        .thenReturn(updateSetDataResponseApplication);

    final UpdateSetDataResponseInfrastructure updateSetDataResponseInfrastructure =
        updateSetDataController.patch(setId, updateSetDataRequestInfrastructure).getBody();
    Assertions.assertEquals(
        updateSetDataResponseApplication.getDescription(),
        updateSetDataResponseInfrastructure.getDescription());
    Assertions.assertEquals(
        updateSetDataResponseApplication.getWeight(),
        updateSetDataResponseInfrastructure.getWeight());
    Assertions.assertEquals(
        updateSetDataResponseApplication.getRir(), updateSetDataResponseInfrastructure.getRir());
    Assertions.assertEquals(
        updateSetDataResponseApplication.getReps(), updateSetDataResponseInfrastructure.getReps());
  }
}
