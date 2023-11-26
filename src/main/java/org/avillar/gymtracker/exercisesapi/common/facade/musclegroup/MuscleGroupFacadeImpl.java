package org.avillar.gymtracker.exercisesapi.common.facade.musclegroup;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclegroup.mapper.MuscleGroupFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MuscleGroupFacadeImpl implements MuscleGroupFacade {

  private final MuscleGroupDao muscleGroupDao;
  private final MuscleGroupFacadeMapper muscleGroupFacadeMapper;

  @Override
  public List<MuscleGroup> getAllMuscleGroupsByMuscleSupGroupId(UUID muscleSupGroupId) {
    return muscleGroupFacadeMapper.map(
        muscleGroupDao.getAllMuscleGroupsByMuscleSupGroupId(muscleSupGroupId));
  }
}
