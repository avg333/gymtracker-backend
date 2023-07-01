package org.avillar.gymtracker.workoutapi.set.createset.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CreateSetController {

  @PostMapping("setGroups/{setGroupId}/sets")
  ResponseEntity<CreateSetResponseInfrastructure> execute(
      @PathVariable UUID setGroupId,
      @RequestBody CreateSetRequestInfrastructure createSetRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
