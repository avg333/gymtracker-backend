package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CreateWorkoutController {

  @Operation(summary = "Create a workout on the user with that id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Created the workout",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateWorkoutResponseInfrastructure.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Workout already exists for that user on that date.",
            content = @Content),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content)
      })
  @PostMapping("/users/{userId}/workouts")
  ResponseEntity<CreateWorkoutResponseInfrastructure> execute(
      @PathVariable UUID userId,
      @Valid @RequestBody CreateWorkoutRequestInfrastructure createWorkoutRequestInfrastructure)
      throws IllegalAccessException, DuplicatedWorkoutDateException;
}
