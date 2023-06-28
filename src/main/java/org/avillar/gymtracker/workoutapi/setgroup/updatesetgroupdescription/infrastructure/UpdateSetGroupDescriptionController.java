package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application.UpdateSetGroupDescriptionService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionResponseInfrastructure;
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
  public ResponseEntity<UpdateSetGroupDescriptionResponseInfrastructure> patch(
      @PathVariable final UUID setGroupId,
      @Valid @RequestBody
          final UpdateSetGroupDescriptionRequestInfrastructure
              updateSetGroupDescriptionRequestInfrastructure) {
    return ResponseEntity.ok(
        new UpdateSetGroupDescriptionResponseInfrastructure(
            updateSetGroupDescriptionService.execute(
                setGroupId, updateSetGroupDescriptionRequestInfrastructure.getDescription())));
  }
}
