package org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.GetMuscleSupGroupService;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.mapper.GetMuscleSupGroupControllerMapper;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.model.GetMuscleSupGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "${exercisesApiPrefix}")
@RequiredArgsConstructor
public class GetMuscleSupGroupController {

  private final GetMuscleSupGroupService getMuscleSupGroupService;
  private final GetMuscleSupGroupControllerMapper getMuscleSupGroupControllerMapper;

  @GetMapping("muscleSupGroups/{muscleSupGroupId}")
  public ResponseEntity<GetMuscleSupGroupResponse> getMuscleSupGroupById(
      @PathVariable final UUID muscleSupGroupId) {
    return ResponseEntity.ok(
        getMuscleSupGroupControllerMapper.getResponse(
            this.getMuscleSupGroupService.getById(muscleSupGroupId)));
  }

  @GetMapping("muscleSupGroups")
  public ResponseEntity<List<GetMuscleSupGroupResponse>> getAllMuscleSupGroups() {
    return ResponseEntity.ok(
        getMuscleSupGroupControllerMapper.getResponse(getMuscleSupGroupService.getAll()));
  }
}
