package org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup.mapper.MuscleSubGroupFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MuscleSubGroupFacadeImpl implements MuscleSubGroupFacade {

  private final MuscleSubGroupDao muscleSubGroupDao;
  private final MuscleSubGroupFacadeMapper muscleSubGroupFacadeMapper;

  @Override
  public List<MuscleSubGroup> getAllMuscleSubGroupsByMuscleGroupId(final UUID muscleGroupId) {
    return muscleSubGroupFacadeMapper.map(muscleSubGroupDao.getAllByMuscleGroupId(muscleGroupId));
  }
}
