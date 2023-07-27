package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.GetMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.mapper.GetAllMuscleSupGroupsControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.model.GetAllMuscleSupGroupsResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetAllMuscleSupGroupsControllerImpl implements GetAllMuscleSupGroupsController {

  private final GetMuscleSupGroupService getMuscleSupGroupService;
  private final GetAllMuscleSupGroupsControllerMapper getAllMuscleSupGroupsControllerMapper;

  @Override
  public List<GetAllMuscleSupGroupsResponse> execute() {
    return getAllMuscleSupGroupsControllerMapper.map(getMuscleSupGroupService.execute());
  }
}
