package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.UpdateSetGroupSetsService;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets.mapper.UpdateSetGroupSetsControllereMapper;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets.model.UpdateSetGroupSetsRequest;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets.model.UpdateSetGroupSetsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateSetGroupSetsController {

  private final UpdateSetGroupSetsService updateSetGroupSetsService;
  private final UpdateSetGroupSetsControllereMapper updateSetGroupSetsControllereMapper;

  @PatchMapping("/setGroups/{setGroupId}/sets")
  public ResponseEntity<UpdateSetGroupSetsResponse> updateSetGroupSets(
      @PathVariable final UUID setGroupId,
      @Valid @RequestBody final UpdateSetGroupSetsRequest updateSetGroupSetsRequest) {
    return ResponseEntity.ok(
        updateSetGroupSetsControllereMapper.updateResponse(
            updateSetGroupSetsService.update(
                setGroupId, updateSetGroupSetsRequest.getSetGroupId())));
  }
}
