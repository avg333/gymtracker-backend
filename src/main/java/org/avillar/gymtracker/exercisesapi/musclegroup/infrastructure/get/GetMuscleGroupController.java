package org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclegroup.application.get.GetMuscleGroupService;
import org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.mapper.GetMuscleGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.model.GetMuscleGroupResponse;
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
  public ResponseEntity<List<GetMuscleGroupResponse>> getAllMuscleGroups(
      @PathVariable final UUID muscleSupGroupId) {
    return ResponseEntity.ok(
        getMuscleGroupControllerMapper.getResponse(
            getMuscleGroupService.getAllByMuscleSupGroupId(muscleSupGroupId)));
  }

  @GetMapping("muscleGroups/{muscleGroupId}")
  public ResponseEntity<GetMuscleGroupResponse> getMuscleGroupById(
      @PathVariable final UUID muscleGroupId) {
    return ResponseEntity.ok(
        getMuscleGroupControllerMapper.getResponse(getMuscleGroupService.getById(muscleGroupId)));
  }

  @GetMapping("muscleGroups")
  public ResponseEntity<List<GetMuscleGroupResponse>> getAllMuscleGroups() {
    return ResponseEntity.ok(
        getMuscleGroupControllerMapper.getResponse(getMuscleGroupService.getAll()));
  }
}
