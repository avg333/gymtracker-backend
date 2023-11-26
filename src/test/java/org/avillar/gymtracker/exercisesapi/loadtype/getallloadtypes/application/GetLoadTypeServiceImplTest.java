package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.common.facade.loadtype.LoadTypeFacade;
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
class GetLoadTypeServiceImplTest {

  @InjectMocks private GetLoadTypeServiceImpl getLoadTypeService;

  @Mock private LoadTypeFacade loadTypeFacade;

  @Test
  void shouldReturnAllLoadTypesSuccessfully() {
    final List<LoadType> facadeResponse = Instancio.createList(LoadType.class);

    when(loadTypeFacade.getAllLoadTypes()).thenReturn(facadeResponse);

    assertThat(getLoadTypeService.execute()).isEqualTo(facadeResponse);
  }
}
