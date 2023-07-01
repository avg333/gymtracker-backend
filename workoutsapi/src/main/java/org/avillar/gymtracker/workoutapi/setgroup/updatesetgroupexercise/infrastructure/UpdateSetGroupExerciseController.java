package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateSetGroupExerciseController {
  @PatchMapping("/setGroups/{setGroupId}/exercise")
  ResponseEntity<UpdateSetGroupExerciseResponseInfrastructure> execute(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody
      UpdateSetGroupExerciseRequestInfrastructure updateSetGroupExerciseRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
