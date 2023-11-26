package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastrucuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.GetLoadTypeService;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.GetAllLoadTypesControllerImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper.GetAllLoadTypesControllerMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetAllLoadTypesControllerImplTest {

  @InjectMocks private GetAllLoadTypesControllerImpl getAllLoadTypesControllerImpl;

  @Mock private GetLoadTypeService getLoadTypeService;
  @Mock private GetAllLoadTypesControllerMapper getLoadTypesControllerMapper;

  @Test
  void shouldReturnAllLoadTypesSuccessfully() {
    final List<LoadType> serviceResponse = Instancio.createList(LoadType.class);
    final List<GetAllLoadTypesResponse> mapperResponse =
        Instancio.createList(GetAllLoadTypesResponse.class);

    when(getLoadTypeService.execute()).thenReturn(serviceResponse);
    when(getLoadTypesControllerMapper.map(serviceResponse)).thenReturn(mapperResponse);

    assertThat(getAllLoadTypesControllerImpl.execute()).isEqualTo(mapperResponse);
  }
}
