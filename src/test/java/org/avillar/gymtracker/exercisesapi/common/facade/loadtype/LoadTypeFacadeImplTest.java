package org.avillar.gymtracker.exercisesapi.common.facade.loadtype;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.LoadTypeEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.common.facade.loadtype.mapper.LoadTypeFacadeMapper;
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
class LoadTypeFacadeImplTest {

  @InjectMocks private LoadTypeFacadeImpl loadTypeFacadeImpl;

  @Mock private LoadTypeDao loadTypeDao;
  @Mock private LoadTypeFacadeMapper loadTypeFacadeMapper;

  @Test
  void shouldReturnAllLoadTypesSuccessfully() {
    final List<LoadTypeEntity> daoResponse = Instancio.createList(LoadTypeEntity.class);
    final List<LoadType> mapperResponse = Instancio.createList(LoadType.class);

    when(loadTypeDao.findAll()).thenReturn(daoResponse);
    when(loadTypeFacadeMapper.map(daoResponse)).thenReturn(mapperResponse);

    assertThat(loadTypeFacadeImpl.getAllLoadTypes()).isEqualTo(mapperResponse);
  }
}
