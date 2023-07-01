package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateWorkoutDescriptionController {

  @Operation(summary = "Modify the org.avillar.gymtracker.workoutapi.workout's description with that id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Description modified",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                      @Schema(
                          implementation = UpdateWorkoutDescriptionResponseInfrastructure.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @PatchMapping("/workouts/{workoutId}/description")
  ResponseEntity<UpdateWorkoutDescriptionResponseInfrastructure> execute(
      @PathVariable UUID workoutId,
      @Valid @RequestBody
      UpdateWorkoutDescriptionRequestInfrastructure
              updateWorkoutDescriptionRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
