package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionRequest;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Workouts", description = "API to manage Workouts")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateWorkoutDescriptionController {

  @Operation(summary = "API used to update the Workout description")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Workout description modified",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UpdateWorkoutDescriptionResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @PatchMapping("/workouts/{workoutId}/description")
  @ResponseStatus(HttpStatus.OK)
  UpdateWorkoutDescriptionResponse execute(
      @PathVariable UUID workoutId,
      @Valid @RequestBody UpdateWorkoutDescriptionRequest updateWorkoutDescriptionRequest)
      throws EntityNotFoundException, IllegalAccessException;
}
