package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model.UpdateSetGroupListOrderResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateSetGroupListOrderController {

  @PatchMapping("/setGroups/{setGroupId}/listOrder")
  ResponseEntity<List<UpdateSetGroupListOrderResponseInfrastructure>> execute(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody
          UpdateSetGroupListOrderRequestInfrastructure updateSetGroupListOrderRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
