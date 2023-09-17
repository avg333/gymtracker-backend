package org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure.model.IncrementExerciseUsesResponseInfrastructure;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Exercises", description = "API to manage Exercises")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface IncrementExerciseUsesController {
  @Operation(summary = "API used to increment the uses of an exercise")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Exercise uses incremented",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                      @Schema(implementation = IncrementExerciseUsesResponseInfrastructure.class))
            })
      })
  @PostMapping("exercises/{exerciseId}/uses/increment")
  @ResponseStatus(HttpStatus.OK)
  IncrementExerciseUsesResponseInfrastructure execute(@PathVariable UUID exerciseId);
}
