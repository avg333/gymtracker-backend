package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetWorkoutDetailsController {

  @Operation(
      summary =
          "Get the org.avillar.gymtracker.workoutapi.workout with that id with all the org.avillar.gymtracker.workoutapi.workout data")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Workout with all the data",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetWorkoutDetailsResponseInfrastructure.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @GetMapping("/workouts/{workoutId}/details")
  ResponseEntity<GetWorkoutDetailsResponseInfrastructure> execute(
      @PathVariable final UUID workoutId);
}
