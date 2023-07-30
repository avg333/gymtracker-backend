package org.avillar.gymtracker.workoutapi.workout.deleteworkout.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Workouts", description = "API to manage Workouts")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface DeleteWorkoutController {

  @Operation(summary = "API used to delete a workout")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Workout deleted"),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @DeleteMapping("/workouts/{workoutId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  Void execute(@PathVariable UUID workoutId) throws EntityNotFoundException, IllegalAccessException;
}
