package org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.mapper.GetMuscleSupGroupApplicationMapper;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model.GetMuscleSupGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.domain.MuscleSupGroupDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMuscleSupGroupServiceImpl implements GetMuscleSupGroupService {

  private final MuscleSupGroupDao muscleSupGroupDao;
  private final GetMuscleSupGroupApplicationMapper getMuscleSupGroupApplicationMapper;

  @Override
  public List<GetMuscleSupGroupsApplicationResponse> execute() {
    return getMuscleSupGroupApplicationMapper.map(muscleSupGroupDao.getAll());
  }
}
