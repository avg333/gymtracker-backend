package org.avillar.gymtracker.exercisesapi.common.facade.musclesupgroup;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleSupGroupDao;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclesupgroup.mapper.MuscleSupGroupFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MuscleSupGroupFacadeImpl implements MuscleSupGroupFacade {

  private final MuscleSupGroupDao muscleSupGroupDao;
  private final MuscleSupGroupFacadeMapper muscleSupGroupFacadeMapper;

  @Override
  public List<MuscleSupGroup> getAllMuscleSupGroups() {
    return muscleSupGroupFacadeMapper.map(muscleSupGroupDao.getAll());
  }
}
