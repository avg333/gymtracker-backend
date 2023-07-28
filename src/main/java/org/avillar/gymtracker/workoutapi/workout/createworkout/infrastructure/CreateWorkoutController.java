package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.exception.application.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Workouts", description = "API to manage Workouts")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CreateWorkoutController {

  @Operation(summary = "API used to create a workout")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Workout created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateWorkoutResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Workout already exists for that user on that date.",
            content = @Content),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content)
      })
  @PostMapping("/users/{userId}/workouts")
  ResponseEntity<CreateWorkoutResponse> execute(
      @PathVariable UUID userId, @Valid @RequestBody CreateWorkoutRequest createWorkoutRequest)
      throws IllegalAccessException, DuplicatedWorkoutDateException;
}
