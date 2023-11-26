package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.ExerciseControllerDocumentation.ExerciseControllerTag;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.GetExercisesByIdsControllerDocumentation.Methods.GetExercisesByIdsDocumentation;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@ExerciseControllerTag
@RequestMapping(path = "${exercisesApiPrefix}/v1")
public interface GetExercisesByIdsController {

  @GetExercisesByIdsDocumentation
  @GetMapping("/exercises")
  @ResponseStatus(HttpStatus.OK)
  List<GetExercisesByIdsResponse> execute(@RequestParam Set<UUID> exerciseIds)
      throws IllegalAccessException;
}
