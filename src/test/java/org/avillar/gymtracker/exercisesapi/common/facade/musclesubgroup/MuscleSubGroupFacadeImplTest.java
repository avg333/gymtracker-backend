package org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSubGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup.mapper.MuscleSubGroupFacadeMapper;
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
class MuscleSubGroupFacadeImplTest {

  @InjectMocks private MuscleSubGroupFacadeImpl muscleSubGroupFacadeImpl;

  @Mock private MuscleSubGroupDao muscleSubGroupDao;
  @Mock private MuscleSubGroupFacadeMapper muscleSubGroupFacadeMapper;

  @Test
  void shouldReturnAllMuscleSubGroupsByMuscleGroupIdSuccessfully() {
    final UUID muscleGroupId = UUID.randomUUID();
    final List<MuscleSubGroupEntity> daoResponse = Instancio.createList(MuscleSubGroupEntity.class);
    final List<MuscleSubGroup> mapperResponse = Instancio.createList(MuscleSubGroup.class);

    when(muscleSubGroupDao.getAllByMuscleGroupId(muscleGroupId)).thenReturn(daoResponse);
    when(muscleSubGroupFacadeMapper.map(daoResponse)).thenReturn(mapperResponse);

    assertThat(muscleSubGroupFacadeImpl.getAllMuscleSubGroupsByMuscleGroupId(muscleGroupId))
        .isEqualTo(mapperResponse);
  }
}
