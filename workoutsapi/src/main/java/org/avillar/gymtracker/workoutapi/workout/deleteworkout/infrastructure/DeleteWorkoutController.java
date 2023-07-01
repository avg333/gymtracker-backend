package org.avillar.gymtracker.workoutapi.workout.deleteworkout.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface DeleteWorkoutController {

  @Operation(summary = "Delete a org.avillar.gymtracker.workoutapi.workout by its id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Deleted the org.avillar.gymtracker.workoutapi.workout"),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @DeleteMapping("/workouts/{workoutId}")
  ResponseEntity<Void> execute(@PathVariable UUID workoutId)
      throws EntityNotFoundException, IllegalArgumentException;
}
