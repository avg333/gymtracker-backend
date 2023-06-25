package org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.GetMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.mapper.GetMuscleSupGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.model.GetMuscleSupGroupsInfrastructureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetMuscleSupGroupsController {

  private final GetMuscleSupGroupService getMuscleSupGroupService;
  private final GetMuscleSupGroupControllerMapper getMuscleSupGroupControllerMapper;

  @GetMapping("muscleSupGroups")
  public ResponseEntity<List<GetMuscleSupGroupsInfrastructureResponse>> get() {
    return ResponseEntity.ok(
        getMuscleSupGroupControllerMapper.map(getMuscleSupGroupService.execute()));
  }
}
