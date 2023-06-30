package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.GetMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.mapper.GetAllMuscleSupGroupsControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.model.GetAllMuscleSupGroupsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GetAllMuscleSupGroupsControllerImpl implements GetAllMuscleSupGroupsController {

  private final GetMuscleSupGroupService getMuscleSupGroupService;
  private final GetAllMuscleSupGroupsControllerMapper getAllMuscleSupGroupsControllerMapper;

  @Override
  public ResponseEntity<List<GetAllMuscleSupGroupsResponseInfrastructure>> execute() {
    return ResponseEntity.ok(
        getAllMuscleSupGroupsControllerMapper.map(getMuscleSupGroupService.execute()));
  }
}
