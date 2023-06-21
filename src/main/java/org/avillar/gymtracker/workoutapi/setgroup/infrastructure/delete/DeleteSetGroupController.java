package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.delete;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.application.delete.DeleteSetGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// RDY
@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class DeleteSetGroupController {

  private final DeleteSetGroupService deleteSetGroupService;

  @DeleteMapping("/setGroups/{setGroupId}")
  public ResponseEntity<Void> deleteSetGroup(@PathVariable final UUID setGroupId) {
    deleteSetGroupService.delete(setGroupId);
    return ResponseEntity.noContent().build();
  }
}
