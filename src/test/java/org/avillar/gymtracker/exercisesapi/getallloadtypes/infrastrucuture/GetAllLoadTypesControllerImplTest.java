package org.avillar.gymtracker.exercisesapi.getallloadtypes.infrastrucuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.GetLoadTypeService;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.GetAllLoadTypesControllerImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper.GetAllLoadTypesControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;
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
class GetAllLoadTypesControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetAllLoadTypesControllerImpl getAllLoadTypesControllerImpl;

  @Mock private GetLoadTypeService getLoadTypeService;
  @Spy private GetAllLoadTypesControllerMapperImpl getLoadTypesControllerMapper;

  @Test
  void get() {
    final List<GetAllLoadTypesResponseApplication> expected =
        easyRandom.objects(GetAllLoadTypesResponseApplication.class, 5).toList();

    when(getLoadTypeService.execute()).thenReturn(expected);

    final ResponseEntity<List<GetAllLoadTypesResponse>> result =
        getAllLoadTypesControllerImpl.execute();
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody()).hasSameSizeAs(expected);
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
  }
}
