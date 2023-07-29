package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.set.getset.application.GetSetService;
import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.mapper.GetSetControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Execution(ExecutionMode.CONCURRENT)
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

    final ResponseEntity<GetSetResponse> result = getSetControllerImpl.execute(expected.getId());
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
