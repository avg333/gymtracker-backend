package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateSetGroupDescriptionController {
  @PatchMapping("/setGroups/{setGroupId}/description")
  ResponseEntity<UpdateSetGroupDescriptionResponseInfrastructure> patch(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody
      UpdateSetGroupDescriptionRequestInfrastructure
          updateSetGroupDescriptionRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
