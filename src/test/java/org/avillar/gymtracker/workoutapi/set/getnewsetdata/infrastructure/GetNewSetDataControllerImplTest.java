package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.GetNewSetDataService;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.model.GetNewSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.mapper.GetNewSetDataControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponse;
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
class GetNewSetDataControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetNewSetDataControllerImpl getNewSetDataControllerImpl;

  @Mock private GetNewSetDataService getNewSetDataService;
  @Spy private GetNewSetDataControllerMapperImpl getNewSetDataControllerMapper;

  @Test
  void getOk() {
    final UUID setId = UUID.randomUUID();
    final GetNewSetDataResponseApplication expected =
        easyRandom.nextObject(GetNewSetDataResponseApplication.class);

    when(getNewSetDataService.execute(setId)).thenReturn(expected);

    final ResponseEntity<GetNewSetDataResponse> result = getNewSetDataControllerImpl.execute(setId);
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
