package org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.GetMuscleGroupService;
import org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.mapper.GetMuscleGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.model.GetMuscleGroupsInfrastructureResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetMuscleGroupController {

  private final GetMuscleGroupService getMuscleGroupService;
  private final GetMuscleGroupControllerMapper getMuscleGroupControllerMapper;

  @GetMapping("muscleSupGroups/{muscleSupGroupId}/muscleGroups")
  public ResponseEntity<List<GetMuscleGroupsInfrastructureResponse>> get(
      @PathVariable final UUID muscleSupGroupId) {
    return ResponseEntity.ok(
        getMuscleGroupControllerMapper.map(getMuscleGroupService.execute(muscleSupGroupId)));
  }
}
