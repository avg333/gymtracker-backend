package org.avillar.gymtracker.exercisesapi.common.facade.musclegroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclegroup.mapper.MuscleGroupFacadeMapper;
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
class MuscleGroupFacadeImplTest {

  @InjectMocks private MuscleGroupFacadeImpl muscleGroupFacadeImpl;

  @Mock private MuscleGroupDao muscleGroupDao;
  @Mock private MuscleGroupFacadeMapper muscleGroupFacadeMapper;

  @Test
  void shouldReturnAllMuscleGroupsByMuscleSupGroupIdSuccessfully() {
    final UUID muscleSupGroupId = UUID.randomUUID();
    final List<MuscleGroupEntity> daoResponse = Instancio.createList(MuscleGroupEntity.class);
    final List<MuscleGroup> mapperResponse = Instancio.createList(MuscleGroup.class);

    when(muscleGroupDao.getAllMuscleGroupsByMuscleSupGroupId(muscleSupGroupId))
        .thenReturn(daoResponse);
    when(muscleGroupFacadeMapper.map(daoResponse)).thenReturn(mapperResponse);

    assertThat(muscleGroupFacadeImpl.getAllMuscleGroupsByMuscleSupGroupId(muscleSupGroupId))
        .isEqualTo(mapperResponse);
  }
}
