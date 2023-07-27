package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Exercises", description = "API to manage Exercises")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetExerciseByIdController {

  @Operation(summary = "API used to get an exercise by its id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Exercises with the id provided",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetExerciseByIdResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Exercise not found", content = @Content)
      })
  @GetMapping("exercises/{exerciseId}")
  @ResponseStatus(HttpStatus.OK)
  GetExerciseByIdResponse execute(@PathVariable UUID exerciseId)
      throws EntityNotFoundException, IllegalAccessException;
}
