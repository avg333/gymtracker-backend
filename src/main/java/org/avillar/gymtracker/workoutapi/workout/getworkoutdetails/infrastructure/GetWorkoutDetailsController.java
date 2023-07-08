package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Workouts", description = "API to manage Workouts")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetWorkoutDetailsController {

  @Operation(summary = "API used to get a workout with its setGroups, sets and exercises")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Workout with all the data",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetWorkoutDetailsResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @GetMapping("/workouts/{workoutId}/details")
  ResponseEntity<GetWorkoutDetailsResponse> execute(@PathVariable final UUID workoutId);
}
