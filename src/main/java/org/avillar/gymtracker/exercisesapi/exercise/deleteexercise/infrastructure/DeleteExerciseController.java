package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Exercises", description = "API to manage Exercises")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface DeleteExerciseController {

  @Operation(summary = "API used to delete an exercise")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Exercise deleted"),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Exercise not found", content = @Content)
      })
  @DeleteMapping("exercises/{exerciseId}")
  ResponseEntity<Void> execute(@PathVariable UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException;
}
