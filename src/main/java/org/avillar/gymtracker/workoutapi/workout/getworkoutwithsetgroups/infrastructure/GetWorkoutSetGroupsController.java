package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetWorkoutSetGroupsController {

  /** GetSGFromWorkout. 1 Retorno simple + 1 retorno con SG (depth = 1) */
  @Operation(summary = "Get org.avillar.gymtracker.workoutapi.workout by its id with its setGroups")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Workout",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                      @Schema(implementation = GetWorkoutSetGroupsResponseInfrastructure.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @GetMapping("/workouts/{workoutId}/sgs") // TODO Definir este endpoint
  ResponseEntity<GetWorkoutSetGroupsResponseInfrastructure> get(@PathVariable UUID workoutId)
      throws EntityNotFoundException, IllegalAccessException;
}
