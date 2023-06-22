package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.setgroup;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.GetSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.setgroup.mapper.GetSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.setgroup.model.GetSetGroupResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class SetGroupController {

  private final GetSetGroupService getSetGroupService;
  private final GetSetGroupControllerMapper getSetGroupControllerMapper;

  @GetMapping("/setGroups/{setGroupId}")
  public ResponseEntity<GetSetGroupResponseInfrastructure> get(
      @PathVariable final UUID setGroupId) {
    return ResponseEntity.ok(
        getSetGroupControllerMapper.map(getSetGroupService.execute(setGroupId)));
  }
}
