package org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.GetMuscleSubGroupService;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.mapper.GetMuscleSubGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.model.GetMuscleSubGroupsInfrastructureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetMuscleSubGroupController {

  private final GetMuscleSubGroupService getMuscleSubGroupService;
  private final GetMuscleSubGroupControllerMapper getMuscleSubGroupControllerMapper;

  @GetMapping("muscleGroups/{muscleGroupId}/muscleSubGroups")
  public ResponseEntity<List<GetMuscleSubGroupsInfrastructureResponse>> get(
      @PathVariable final UUID muscleGroupId) {
    return ResponseEntity.ok(
        getMuscleSubGroupControllerMapper.map(getMuscleSubGroupService.execute(muscleGroupId)));
  }
}
