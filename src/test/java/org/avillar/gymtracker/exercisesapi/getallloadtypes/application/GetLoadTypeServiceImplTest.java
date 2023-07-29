package org.avillar.gymtracker.exercisesapi.getallloadtypes.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.domain.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.GetLoadTypeServiceImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.mapper.GetAllLoadTypesServiceMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetLoadTypeServiceImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetLoadTypeServiceImpl getLoadTypeService;

  @Mock private LoadTypeDao loadTypeDao;

  @Spy
  private final GetAllLoadTypesServiceMapper getAllLoadTypesServiceMapper =
      Mappers.getMapper(GetAllLoadTypesServiceMapper.class);

  @Test
  void execute() {
    final List<LoadType> expected = easyRandom.objects(LoadType.class, LIST_SIZE).toList();

    when(loadTypeDao.findAll()).thenReturn(expected);

    final List<GetAllLoadTypesResponseApplication> result = getLoadTypeService.execute();
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
