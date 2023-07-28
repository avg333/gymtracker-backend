package org.avillar.gymtracker.exercisesapi.getallloadtypes.infrastrucuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.GetLoadTypeService;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.GetAllLoadTypesControllerImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper.GetAllLoadTypesControllerMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllLoadTypesControllerImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetAllLoadTypesControllerImpl getAllLoadTypesControllerImpl;

  @Mock private GetLoadTypeService getLoadTypeService;

  @Spy
  private final GetAllLoadTypesControllerMapper getLoadTypesControllerMapper =
      Mappers.getMapper(GetAllLoadTypesControllerMapper.class);

  @Test
  void get() {
    final List<GetAllLoadTypesResponseApplication> expected =
        easyRandom.objects(GetAllLoadTypesResponseApplication.class, LIST_SIZE).toList();

    when(getLoadTypeService.execute()).thenReturn(expected);

    final List<GetAllLoadTypesResponse> result = getAllLoadTypesControllerImpl.execute();
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
