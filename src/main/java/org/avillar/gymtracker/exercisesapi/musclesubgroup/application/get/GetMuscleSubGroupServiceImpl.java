package org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.mapper.GetMuscleSubGroupServiceMapper;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model.GetMuscleSubGroupResponse;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.domain.MuscleSubGroupDao;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMuscleSubGroupServiceImpl implements GetMuscleSubGroupService {

  private final MuscleSubGroupDao muscleSubGroupDao;

  private final GetMuscleSubGroupServiceMapper getMuscleSubGroupServiceMapper;

  @Override
  public GetMuscleSubGroupResponse getById(final UUID muscleSubGroupId) {
    return getMuscleSubGroupServiceMapper.getResponse(
        muscleSubGroupDao
            .findById(muscleSubGroupId)
            .orElseThrow(
                () -> new EntityNotFoundException(MuscleSubGroup.class, muscleSubGroupId)));
  }

  @Override
  public List<GetMuscleSubGroupResponse> getAllByMuscleGroupId(final UUID muscleGroupId) {
    // TODO Deberia comprobarse si existe el muscleGroup?

    return getMuscleSubGroupServiceMapper.getResponse(
        this.muscleSubGroupDao.getAllByMuscleGroupId(muscleGroupId));
  }
}
