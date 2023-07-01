package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetExercisesByIdsController {

  @GetMapping("exercises")
  ResponseEntity<List<GetExercisesByIdsResponseInfrastructure>> execute(
      @RequestParam Set<UUID> exerciseIds);
}