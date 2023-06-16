package org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.mapper.GetMuscleSupGroupApplicationMapper;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model.GetMuscleSupGroupResponse;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.domain.MuscleSupGroupDao;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMuscleSupGroupServiceImpl implements GetMuscleSupGroupService {

  private final MuscleSupGroupDao muscleSupGroupDao;
  private final GetMuscleSupGroupApplicationMapper getMuscleSupGroupApplicationMapper;

  @Override
  public GetMuscleSupGroupResponse getById(final UUID muscleSupGroupId) {
    return getMuscleSupGroupApplicationMapper.getResponse(
        muscleSupGroupDao
            .findById(muscleSupGroupId)
            .orElseThrow(
                () -> new EntityNotFoundException(MuscleSupGroup.class, muscleSupGroupId)));
  }

  @Override
  public List<GetMuscleSupGroupResponse> getAll() {
    return getMuscleSupGroupApplicationMapper.getResponse(muscleSupGroupDao.findAll());
  }
}
