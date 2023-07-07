package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application.UpdateSetGroupDescriptionService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdateSetGroupDescriptionControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private UpdateSetGroupDescriptionControllerImpl updateSetGroupDescriptionControllerImpl;

  @Mock private UpdateSetGroupDescriptionService updateSetGroupDescriptionService;

  @Test
  void putSetGroup() {
    final UUID workoutId = UUID.randomUUID();
    final UpdateSetGroupDescriptionRequest expected =
        easyRandom.nextObject(UpdateSetGroupDescriptionRequest.class);

    when(updateSetGroupDescriptionService.execute(workoutId, expected.getDescription()))
        .thenReturn(expected.getDescription());

    final ResponseEntity<UpdateSetGroupDescriptionResponse> result =
        updateSetGroupDescriptionControllerImpl.execute(workoutId, expected);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.getDescription(), result.getBody().getDescription());
  }
}
