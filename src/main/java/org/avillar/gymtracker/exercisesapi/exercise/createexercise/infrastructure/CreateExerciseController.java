package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.ExerciseControllerDocumentation.ExerciseControllerTag;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.CreateExerciseControllerDocumentation.Methods.CreateDocumentation;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ExerciseControllerTag
@RequestMapping(path = "${exercisesApiPrefix}/v1")
public interface CreateExerciseController {

  @CreateDocumentation
  @PostMapping("/users/{userId}/exercises")
  @ResponseStatus(HttpStatus.OK)
  CreateExerciseResponse execute(
      @PathVariable UUID userId, @Valid @RequestBody CreateExerciseRequest createExerciseRequest)
      throws EntityNotFoundException, IllegalAccessException;
}
