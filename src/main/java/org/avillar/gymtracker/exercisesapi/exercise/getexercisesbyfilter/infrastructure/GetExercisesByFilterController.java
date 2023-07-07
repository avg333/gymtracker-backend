package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Exercises", description = "API to manage Exercises")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetExercisesByFilterController {

  @GetMapping("exercises/filter")
  ResponseEntity<List<GetExercisesByFilterResponseInfrastructure>> execute(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) Boolean unilateral,
      @RequestParam(required = false) List<UUID> loadTypeIds,
      @RequestParam(required = false) List<UUID> muscleSupGroupIds,
      @RequestParam(required = false) List<UUID> muscleGroupId,
      @RequestParam(required = false) List<UUID> muscleSubGroupIds);
}
