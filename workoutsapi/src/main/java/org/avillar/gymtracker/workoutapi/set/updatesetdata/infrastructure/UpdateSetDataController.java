package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateSetDataController {
  @PatchMapping("sets/{setId}")
  ResponseEntity<UpdateSetDataResponseInfrastructure> patch(
      @PathVariable UUID setId,
      @RequestBody UpdateSetDataRequestInfrastructure updateSetDataRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
