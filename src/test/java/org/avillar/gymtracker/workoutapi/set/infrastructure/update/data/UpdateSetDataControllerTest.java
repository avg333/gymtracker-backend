package org.avillar.gymtracker.workoutapi.set.infrastructure.update.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.application.update.data.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.mapper.UpdateSetDataControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.infrastructure.update.data.model.UpdateSetDataResponseInfrastructure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateSetDataControllerTest {

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
    final UUID setId = UUID.randomUUID();
    final String description = "Description example 54.";
    final double weight = 115.2;
    final double rir = 8.0;
    final int reps = 6;
    final UpdateSetDataRequestInfrastructure updateSetDataRequestInfrastructure =
        new UpdateSetDataRequestInfrastructure();
    updateSetDataRequestInfrastructure.setDescription(description);
    updateSetDataRequestInfrastructure.setWeight(weight);
    updateSetDataRequestInfrastructure.setRir(rir);
    updateSetDataRequestInfrastructure.setReps(reps);

    when(updateSetDataService.update(eq(setId), any(UpdateSetDataRequestApplication.class)))
        .thenReturn(new UpdateSetDataResponseApplication(description, reps, rir, weight));

    final UpdateSetDataResponseInfrastructure updateSetDataResponseInfrastructure =
        updateSetDataController.updateSetData(setId, updateSetDataRequestInfrastructure).getBody();
    Assertions.assertEquals(description, updateSetDataResponseInfrastructure.getDescription());
    Assertions.assertEquals(weight, updateSetDataResponseInfrastructure.getWeight());
    Assertions.assertEquals(rir, updateSetDataResponseInfrastructure.getRir());
    Assertions.assertEquals(reps, updateSetDataResponseInfrastructure.getReps());
  }
}
