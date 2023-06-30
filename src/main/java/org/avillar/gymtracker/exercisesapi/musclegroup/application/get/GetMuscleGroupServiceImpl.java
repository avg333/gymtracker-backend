package org.avillar.gymtracker.exercisesapi.musclegroup.application.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.mapper.GetMuscleGroupServiceMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model.GetMuscleGroupsApplicationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMuscleGroupServiceImpl implements GetMuscleGroupService {

  private final MuscleGroupDao muscleGroupDao;
  private final GetMuscleGroupServiceMapper getMuscleGroupServiceMapper;

  @Override
  public List<GetMuscleGroupsApplicationResponse> execute(UUID muscleSupGroupId) {
    return getMuscleGroupServiceMapper.map(
        muscleGroupDao.getALlMuscleGroupsByMuscleSupGroupId(muscleSupGroupId));
  }
}
