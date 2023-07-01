package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateSetListOrderController {
  @PatchMapping("sets/{setId}/listOrder")
  ResponseEntity<List<UpdateSetListOrderResponseInfrastructure>> execute(
      @PathVariable UUID setId,
      @RequestBody UpdateSetListOrderRequestInfrastructure updateSetListOrderRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
