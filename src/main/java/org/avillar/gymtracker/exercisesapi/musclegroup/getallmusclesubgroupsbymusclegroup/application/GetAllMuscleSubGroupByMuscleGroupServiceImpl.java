package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup.MuscleSubGroupFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllMuscleSubGroupByMuscleGroupServiceImpl
    implements GetAllMuscleSubGroupByMuscleGroupService {

  private final MuscleSubGroupFacade muscleSubGroupFacade;

  @Override
  public List<MuscleSubGroup> execute(final UUID muscleGroupId) {
    return muscleSubGroupFacade.getAllMuscleSubGroupsByMuscleGroupId(muscleGroupId);
  }
}
