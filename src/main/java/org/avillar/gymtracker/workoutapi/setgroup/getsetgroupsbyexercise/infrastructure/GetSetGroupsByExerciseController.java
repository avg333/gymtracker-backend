package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetSetGroupsByExerciseController {

  @GetMapping("/users/{userId}/exercises/{exerciseId}/setGroups")
  ResponseEntity<List<GetSetGroupsByExerciseResponseInfrastructure>> execute(
      @PathVariable UUID userId, @PathVariable UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException;
}
