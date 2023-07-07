package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Exercises", description = "API to manage Exercises")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetExerciseByIdController {

  @GetMapping("exercises/{exerciseId}")
  ResponseEntity<GetExerciseByIdResponseInfrastructure> execute(@PathVariable UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException;
}
