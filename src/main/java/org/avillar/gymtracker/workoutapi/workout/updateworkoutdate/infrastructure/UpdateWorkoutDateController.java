package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateWorkoutDateController {

  @Operation(summary = "Modify the workout's date with that id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Date modified",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UpdateWorkoutDateResponseInfrastructure.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "A workout already exists for that user on that date.",
            content = @Content),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @PatchMapping("/workouts/{workoutId}/date")
  ResponseEntity<UpdateWorkoutDateResponseInfrastructure> execute(
      @PathVariable UUID workoutId,
      @Valid @RequestBody
          UpdateWorkoutDateRequestInfrastructure updateWorkoutDateRequestInfrastructure)
      throws EntityNotFoundException, DuplicatedWorkoutDateException, IllegalAccessException;
}
