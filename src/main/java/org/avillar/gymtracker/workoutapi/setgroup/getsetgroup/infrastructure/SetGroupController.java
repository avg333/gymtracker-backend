package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface SetGroupController {
  @GetMapping("/setGroups/{setGroupId}")
  ResponseEntity<GetSetGroupResponseInfrastructure> get(@PathVariable UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException;
}
