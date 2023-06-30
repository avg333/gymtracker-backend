package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CopySetGroupsController {

  @PatchMapping("/workouts/{workoutId}/copySetGroups")
  ResponseEntity<List<CopySetGroupsResponseInfrastructure>> execute(
      @PathVariable UUID workoutId,
      @Valid @RequestBody CopySetGroupsRequestInfrastructure copySetGroupsRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
