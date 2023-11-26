package org.avillar.gymtracker.exercisesapi.common.facade.musclesupgroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleSupGroupDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSupGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclesupgroup.mapper.MuscleSupGroupFacadeMapper;
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
class MuscleSupGroupFacadeImplTest {

  @InjectMocks private MuscleSupGroupFacadeImpl muscleSupGroupFacadeImpl;

  @Mock private MuscleSupGroupDao muscleSupGroupDao;
  @Mock private MuscleSupGroupFacadeMapper muscleSupGroupFacadeMapper;

  @Test
  void shouldReturnAllMuscleSupGroupsSuccessfully() {
    final List<MuscleSupGroupEntity> daoResponse = Instancio.createList(MuscleSupGroupEntity.class);
    final List<MuscleSupGroup> mapperResponse = Instancio.createList(MuscleSupGroup.class);

    when(muscleSupGroupDao.getAll()).thenReturn(daoResponse);
    when(muscleSupGroupFacadeMapper.map(daoResponse)).thenReturn(mapperResponse);

    assertThat(muscleSupGroupFacadeImpl.getAllMuscleSupGroups()).isEqualTo(mapperResponse);
  }
}
