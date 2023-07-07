package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.GetSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper.GetSetGroupControllerMapperImpl;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponse;
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
class SetGroupControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private SetGroupControllerImpl setGroupControllerImpl;

  @Mock private GetSetGroupService getSetGroupService;
  @Spy private GetSetGroupControllerMapperImpl getSetGroupControllerMapper;

  @Test
  void getOk() {
    final GetSetGroupResponseApplication expected =
        easyRandom.nextObject(GetSetGroupResponseApplication.class);

    when(getSetGroupService.execute(expected.getId())).thenReturn(expected);

    final ResponseEntity<GetSetGroupResponse> result =
        setGroupControllerImpl.execute(expected.getId());
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
