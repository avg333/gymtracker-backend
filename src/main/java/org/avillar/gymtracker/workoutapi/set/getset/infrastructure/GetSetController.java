package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetSetController {
  @GetMapping("sets/{setId}")
  ResponseEntity<GetSetResponseInfrastructure> execute(@PathVariable UUID setId)
      throws EntityNotFoundException, IllegalAccessException;
}
