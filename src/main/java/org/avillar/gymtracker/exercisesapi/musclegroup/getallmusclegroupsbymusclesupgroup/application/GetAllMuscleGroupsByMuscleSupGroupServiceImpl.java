package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.mapper.GetAllMuscleGroupsByMuscleSupGroupServiceMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model.GetAllMuscleGroupsByMuscleSupGroupResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllMuscleGroupsByMuscleSupGroupServiceImpl
    implements GetAllMuscleGroupsByMuscleSupGroupService {

  private final MuscleGroupDao muscleGroupDao;
  private final GetAllMuscleGroupsByMuscleSupGroupServiceMapper
      getAllMuscleGroupsByMuscleSupGroupServiceMapper;

  @Override
  public List<GetAllMuscleGroupsByMuscleSupGroupResponseApplication> execute(
      final UUID muscleSupGroupId) {
    return getAllMuscleGroupsByMuscleSupGroupServiceMapper.map(
        muscleGroupDao.getALlMuscleGroupsByMuscleSupGroupId(muscleSupGroupId));
  }
}
