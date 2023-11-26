package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclegroup.MuscleGroupFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllMuscleGroupsByMuscleSupGroupServiceImpl
    implements GetAllMuscleGroupsByMuscleSupGroupService {

  private final MuscleGroupFacade muscleGroupFacade;

  @Override
  public List<MuscleGroup> execute(final UUID muscleSupGroupId) {
    return muscleGroupFacade.getAllMuscleGroupsByMuscleSupGroupId(muscleSupGroupId);
  }
}
