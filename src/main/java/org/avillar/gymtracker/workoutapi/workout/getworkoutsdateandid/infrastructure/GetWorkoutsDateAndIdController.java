package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Workouts", description = "API to manage Workouts")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetWorkoutsDateAndIdController {

  @Operation(
      summary = "API used to get all the user workouts date and id (can be filtered by exerciseId)")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Map {date: id} of the user workouts",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetWorkoutsDateAndIdResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
      })
  @GetMapping("/users/{userId}/workouts/dates")
  ResponseEntity<GetWorkoutsDateAndIdResponse> execute(
      @PathVariable UUID userId, @RequestParam(required = false) UUID exerciseId)
      throws IllegalAccessException;
}
