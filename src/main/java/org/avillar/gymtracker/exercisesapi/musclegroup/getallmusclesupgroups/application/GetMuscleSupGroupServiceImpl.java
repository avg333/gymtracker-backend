package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.common.facade.musclesupgroup.MuscleSupGroupFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMuscleSupGroupServiceImpl implements GetMuscleSupGroupService {

  private final MuscleSupGroupFacade muscleSupGroupFacade;

  @Override
  public List<MuscleSupGroup> execute() {
    return muscleSupGroupFacade.getAllMuscleSupGroups();
  }
}
