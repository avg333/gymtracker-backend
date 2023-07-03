package org.avillar.gymtracker.workoutsapi.set.updatesetdata.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.UpdateSetDataService;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.UpdateSetDataControllerImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.mapper.UpdateSetDataControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseInfrastructure;
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
class UpdateSetDataControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UpdateSetDataControllerImpl updateSetDataControllerImpl;
  @Mock private UpdateSetDataService updateSetDataService;
  @Spy private UpdateSetDataControllerMapperImpl updateSetDataControllerMapper;

  @Test
  void updateSetData() {
    final UpdateSetDataResponseApplication expected =
        easyRandom.nextObject(UpdateSetDataResponseApplication.class);
    final UUID setId = UUID.randomUUID();
    final UpdateSetDataRequestInfrastructure updateSetDataRequestInfrastructure =
        new UpdateSetDataRequestInfrastructure();
    updateSetDataRequestInfrastructure.setDescription(expected.getDescription());
    updateSetDataRequestInfrastructure.setWeight(expected.getWeight());
    updateSetDataRequestInfrastructure.setRir(expected.getRir());
    updateSetDataRequestInfrastructure.setReps(expected.getReps());

    when(updateSetDataService.execute(
            setId, updateSetDataControllerMapper.map(updateSetDataRequestInfrastructure)))
        .thenReturn(expected);

    final ResponseEntity<UpdateSetDataResponseInfrastructure> result =
        updateSetDataControllerImpl.patch(setId, updateSetDataRequestInfrastructure);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.getDescription(), result.getBody().getDescription());
    assertEquals(expected.getWeight(), result.getBody().getWeight());
    assertEquals(expected.getRir(), result.getBody().getRir());
    assertEquals(expected.getReps(), result.getBody().getReps());
  }
}
