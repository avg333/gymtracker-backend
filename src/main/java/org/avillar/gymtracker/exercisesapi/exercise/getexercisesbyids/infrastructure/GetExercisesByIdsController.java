package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Exercises", description = "API to manage Exercises")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetExercisesByIdsController {

  @Operation(summary = "API used to get exercises by their ids")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Exercises with the ids provided",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetExercisesByIdsResponseInfrastructure.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content)
      })
  @GetMapping("exercises")
  ResponseEntity<List<GetExercisesByIdsResponseInfrastructure>> execute(
      @RequestParam Set<UUID> exerciseIds) throws IllegalAccessException;
}
