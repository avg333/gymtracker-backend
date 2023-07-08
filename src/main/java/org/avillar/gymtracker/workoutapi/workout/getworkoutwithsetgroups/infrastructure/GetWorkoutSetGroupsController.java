package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Workouts", description = "API to manage Workouts")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetWorkoutSetGroupsController {

  @Operation(summary = "API used to get a workout with its setGroups")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Workout with its setGroups",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetWorkoutSetGroupsResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @GetMapping("/workouts/{workoutId}/sgs") // TODO Definir este endpoint
  ResponseEntity<GetWorkoutSetGroupsResponse> get(@PathVariable UUID workoutId)
      throws EntityNotFoundException, IllegalAccessException;
}
