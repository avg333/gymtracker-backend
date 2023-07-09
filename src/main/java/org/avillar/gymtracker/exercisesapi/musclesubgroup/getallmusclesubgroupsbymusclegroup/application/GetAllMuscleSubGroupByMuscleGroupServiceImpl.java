package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.mapper.GetAllMuscleSubGroupByMuscleGroupServiceMapper;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.model.GetAllMuscleSubGroupByMuscleGroupResponseApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
@RequiredArgsConstructor
public class GetAllMuscleSubGroupByMuscleGroupServiceImpl
    implements GetAllMuscleSubGroupByMuscleGroupService {

  private final MuscleSubGroupDao muscleSubGroupDao;
  private final GetAllMuscleSubGroupByMuscleGroupServiceMapper
      getAllMuscleSubGroupByMuscleGroupServiceMapper;

  @Cacheable(cacheNames = "muscleSubGroups")
  @Override
  public List<GetAllMuscleSubGroupByMuscleGroupResponseApplication> execute(
      final UUID muscleGroupId) {
    return getAllMuscleSubGroupByMuscleGroupServiceMapper.map(
        this.muscleSubGroupDao.getAllByMuscleGroupId(muscleGroupId));
  }
}
