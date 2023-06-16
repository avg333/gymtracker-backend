package org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.GetMuscleSubGroupService;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.mapper.GetMuscleSubGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.model.GetMuscleSubGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetMuscleSubGroupController {

  private final GetMuscleSubGroupService getMuscleSubGroupService;
  private final GetMuscleSubGroupControllerMapper getMuscleSubGroupControllerMapper;

  @GetMapping("muscleSubGroups/{muscleSubGroupId}")
  public ResponseEntity<GetMuscleSubGroupResponse> getById(
      @PathVariable final UUID muscleSubGroupId) {
    return ResponseEntity.ok(
        getMuscleSubGroupControllerMapper.getResponse(
            getMuscleSubGroupService.getById(muscleSubGroupId)));
  }

  @GetMapping("muscleGroups/{muscleGroupId}/muscleSubGroups")
  public ResponseEntity<List<GetMuscleSubGroupResponse>> getAllByMuscleGroup(
      @PathVariable final UUID muscleGroupId) {
    return ResponseEntity.ok(
        getMuscleSubGroupControllerMapper.getResponse(
            getMuscleSubGroupService.getAllByMuscleGroupId(muscleGroupId)));
  }
}
