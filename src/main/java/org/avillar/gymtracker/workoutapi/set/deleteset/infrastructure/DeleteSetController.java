package org.avillar.gymtracker.workoutapi.set.deleteset.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface DeleteSetController {

  @DeleteMapping("sets/{setId}")
  ResponseEntity<Void> execute(@PathVariable UUID setId)
      throws EntityNotFoundException, IllegalAccessException;
}
