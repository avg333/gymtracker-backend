package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.description;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.description.UpdateSetGroupDescriptionService;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.description.model.UpdateSetGroupDescriptionRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.description.model.UpdateSetGroupDescriptionResponseInfrastructure;
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
  public ResponseEntity<UpdateSetGroupDescriptionResponseInfrastructure> updateSetGroupDescription(
      @PathVariable final UUID setGroupId,
      @Valid @RequestBody
          final UpdateSetGroupDescriptionRequestInfrastructure
              updateSetGroupDescriptionRequestInfrastructure) {
    return ResponseEntity.ok(
        new UpdateSetGroupDescriptionResponseInfrastructure(
            updateSetGroupDescriptionService.update(
                setGroupId, updateSetGroupDescriptionRequestInfrastructure.getDescription())));
  }
}
