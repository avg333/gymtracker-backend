package org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.application.UpdateSetGroupSetsService;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.mapper.UpdateSetGroupSetsControllereMapper;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.infrastrucure.model.UpdateSetGroupSetsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// FINALIZAR
@Tag(name = "SetGroups", description = "API to manage setGroups")
@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateSetGroupSetsController {

  private final UpdateSetGroupSetsService updateSetGroupSetsService;
  private final UpdateSetGroupSetsControllereMapper updateSetGroupSetsControllereMapper;

  // TODO Debe devolver el setGroup solo (con la sets dentro si se considera)
  @PatchMapping("/setGroups/{setGroupId}/sets")
  public ResponseEntity<UpdateSetGroupSetsResponseInfrastructure> execute(
      @PathVariable final UUID setGroupId,
      @Valid @RequestBody
          final UpdateSetGroupSetsRequestInfrastructure updateSetGroupSetsRequestInfrastructure) {
    return ResponseEntity.ok(
        updateSetGroupSetsControllereMapper.map(
            updateSetGroupSetsService.execute(
                setGroupId, updateSetGroupSetsRequestInfrastructure.getSetGroupId())));
  }
}
