package org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.mapper.GetMuscleSubGroupServiceMapper;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model.GetMuscleSubGroupsApplicationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMuscleSubGroupServiceImpl implements GetMuscleSubGroupService {

  private final MuscleSubGroupDao muscleSubGroupDao;

  private final GetMuscleSubGroupServiceMapper getMuscleSubGroupServiceMapper;

  @Override
  public List<GetMuscleSubGroupsApplicationResponse> execute(final UUID muscleGroupId) {
    // TODO Deberia comprobarse si existe el muscleGroup?

    return getMuscleSubGroupServiceMapper.map(
        this.muscleSubGroupDao.getAllByMuscleGroupId(muscleGroupId));
  }
}
