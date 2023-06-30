package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroupDao;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.mapper.GetAllMuscleSupGroupsApplicationMapper;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMuscleSupGroupServiceImpl implements GetMuscleSupGroupService {

  private final MuscleSupGroupDao muscleSupGroupDao;
  private final GetAllMuscleSupGroupsApplicationMapper getAllMuscleSupGroupsApplicationMapper;

  @Override
  public List<GetAllMuscleSupGroupsResponseApplication> execute() {
    return getAllMuscleSupGroupsApplicationMapper.map(muscleSupGroupDao.getAll());
  }
}
