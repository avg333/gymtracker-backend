package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.description;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.description.UpdateSetGroupDescriptionService;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.description.model.UpdateSetGroupDescriptionRequest;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.description.model.UpdateSetGroupDescriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateSetGroupDescriptionController {

  private final UpdateSetGroupDescriptionService updateSetGroupDescriptionService;

  @PatchMapping("/setGroups/{setGroupId}/description")
  public ResponseEntity<UpdateSetGroupDescriptionResponse> putSetGroup(
      @PathVariable final UUID setGroupId,
      @Valid @RequestBody final UpdateSetGroupDescriptionRequest updateSetGroupDescriptionRequest) {
    return ResponseEntity.ok(
        new UpdateSetGroupDescriptionResponse(
            updateSetGroupDescriptionService.update(
                setGroupId, updateSetGroupDescriptionRequest.getDescription())));
  }
}
