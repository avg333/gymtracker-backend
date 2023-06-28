package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CreteSetGroupController {

  @PostMapping("/workouts/{workoutId}/setGroups")
  ResponseEntity<CreateSetGroupResponseInfrastructure> execute(
      @PathVariable UUID workoutId,
      @Valid @RequestBody CreateSetGroupRequestInfrastructure createSetGroupRequestInfrastructure);
}
