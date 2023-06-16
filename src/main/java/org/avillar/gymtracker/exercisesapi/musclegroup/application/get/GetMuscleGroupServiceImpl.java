package org.avillar.gymtracker.exercisesapi.musclegroup.application.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.mapper.GetMuscleGroupServiceMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model.GetMuscleGroupResponse;
import org.avillar.gymtracker.exercisesapi.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.domain.MuscleGroupDao;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMuscleGroupServiceImpl implements GetMuscleGroupService {

  private final MuscleGroupDao muscleGroupDao;
  private final GetMuscleGroupServiceMapper getMuscleGroupServiceMapper;

  @Override
  public GetMuscleGroupResponse getById(final UUID muscleGroupId) {
    return getMuscleGroupServiceMapper.getResponse(
        muscleGroupDao
            .findById(muscleGroupId)
            .orElseThrow(() -> new EntityNotFoundException(MuscleGroup.class, muscleGroupId)));
  }

  @Override
  public List<GetMuscleGroupResponse> getAll() {
    return getMuscleGroupServiceMapper.getResponse(muscleGroupDao.findAll());
  }
}
